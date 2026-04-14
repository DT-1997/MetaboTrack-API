package com.metabotrackapi.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.metabotrackapi.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    /**
     * Encrypts the password
     * * @param rawPassword the raw password to encode
     * @return the encoded password string containing the salt
     */
    public String encode(String rawPassword) {
        // 1. Generate a 20-character random salt
        String salt = RandomUtil.randomString(20);
        return encode(rawPassword, salt);
    }

    /**
     * Verifies if the raw password matches the encoded password
     * * @param encodedPassword the encoded password (usually from the database)
     * @param rawPassword the raw password input by the user
     * @return true if the passwords match, false otherwise
     */
    public boolean matches(String encodedPassword, String rawPassword) {
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }

        // Verify if the format is correct
        if (!encodedPassword.contains("@")) {
            throw new BusinessException("Invalid password format, unable to parse salt");
        }

        // 1. Extract the salt
        String[] arr = encodedPassword.split("@");
        String salt = arr[0];

        // 2. Encrypt the input password using the extracted salt, and check if the result matches the one stored in the database
        return encodedPassword.equals(encode(rawPassword, salt));
    }

    /**
     * Core encryption logic
     * * @param rawPassword the raw password
     * @param salt the salt value
     * @return the concatenated string of salt and hashed password
     */
    private String encode(String rawPassword, String salt) {
        return salt + "@" + DigestUtil.sha256Hex(rawPassword + salt);
    }
}