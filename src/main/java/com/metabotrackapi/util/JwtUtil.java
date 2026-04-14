package com.metabotrackapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT Utility Class
 */
public class JwtUtil {

    /**
     * Generates a JWT Token
     *
     * @param secretKey The secret key used for signing
     * @param ttlMillis Time to live in milliseconds
     * @param claims    The payload claims
     * @return The generated JWT token string
     */
    public static String createJWT(String secretKey, Long ttlMillis, Map<String, Object> claims) {
        // 1. Convert the secret key string into a SecretKey object
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 2. Calculate the expiration time
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 3. Build the Token
        return Jwts.builder()
                .claims(claims)
                .expiration(exp)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Parses a JWT Token
     * * @param secretKey The secret key used for signing
     * @param token     The JWT token string to parse
     * @return The parsed Claims payload
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 1. Convert the secret key string into a SecretKey object
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 2. Build the parser and parse the token
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}