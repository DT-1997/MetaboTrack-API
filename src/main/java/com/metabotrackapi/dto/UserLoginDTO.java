package com.metabotrackapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for user authentication")
public class UserLoginDTO {

    @Schema(description = "Account username for login", example = "user_1001")
    private String username;

    @Schema(description = "Account password", example = "12345678")
    private String password;

}
