package com.metabotrackapi.controller;

import com.metabotrackapi.dto.UserLoginDTO;
import com.metabotrackapi.dto.UserRegisterDTO;
import com.metabotrackapi.result.Result;
import com.metabotrackapi.service.UserService;
import com.metabotrackapi.util.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Authentication", description = "Endpoints for managing user accounts, including registration, login, and logout.")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "User Registration", description = "Register a new user account. Passwords will be securely hashed before storage.")
    public Result<Boolean> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("User registration attempt for user: {}", userRegisterDTO.getUsername());
        boolean success = userService.register(userRegisterDTO);
        return success ? Result.success(true) : Result.error("Registration failed");
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticate credentials and receive a JWT token for subsequent API requests.")
    public Result<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("User login attempt for username: {}", userLoginDTO.getUsername());
        String token = userService.login(userLoginDTO);
        return Result.success(token);
    }

    @PostMapping("/logout")
    @Operation(summary = "User Logout", description = "Acknowledge logout request. The client application MUST discard the JWT locally to complete the logout process.")
    public Result<String> logout() {
        log.info("User logout initiated for ID: {}", UserContext.getCurrentId());
        return Result.success("Logout successful");
    }
}
