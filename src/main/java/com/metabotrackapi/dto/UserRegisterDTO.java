package com.metabotrackapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for new user registration")
public class UserRegisterDTO {

    @Schema(description = "Unique username for the new account", example = "user_0")
    private String username;

    @Schema(description = "Secure password for the new account", example = "12345678")
    private String password;
}