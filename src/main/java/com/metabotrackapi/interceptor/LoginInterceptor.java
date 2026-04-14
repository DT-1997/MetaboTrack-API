package com.metabotrackapi.interceptor;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metabotrackapi.properties.JwtProperties;
import com.metabotrackapi.result.Result;
import com.metabotrackapi.util.JwtUtil;
import com.metabotrackapi.util.UserContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check if the intercepted handler is a Controller method
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // Allow CORS preflight OPTIONS requests to pass through
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // Retrieve the token from the request header
        String token = request.getHeader(jwtProperties.getTokenName());

        try {
            // Handle the "Bearer " prefix if present
            if (StrUtil.isNotBlank(token) && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            log.info("Starting token validation: {}", token);

            // Parse the token
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long userId = Long.valueOf(claims.get("userId").toString());

            log.info("Current logged-in user ID: {}", userId);

            // Store the user ID in the ThreadLocal context
            UserContext.setCurrentId(userId);

            return true;

        } catch (Exception ex) {
            log.error("Authentication failed: {}", ex.getMessage());

            // Set HTTP status code to 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Set response content type to JSON with UTF-8 encoding
            response.setContentType("application/json;charset=utf-8");

            // Construct a unified error response
            Result<String> errorResult = Result.error(401, "Please log in to access this resource");

            // Serialize the response object to JSON and write it to the response body
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorResult);
            response.getWriter().write(jsonResponse);

            // Block the request from proceeding to the Controller
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // Clear the user ID from ThreadLocal to prevent memory leaks
        UserContext.removeCurrentId();
    }
}