package com.metabotrackapi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Gamified ranking data comparing a specific user against the entire population")
public class UserPercentileVO {

    @Schema(description = "The requested user ID", example = "1001")
    private Long userId;

    @Schema(description = "The user's historical average efficiency score", example = "8.95")
    private Double personalAvgScore;

    @Schema(description = "The calculated percentile (e.g., 85.5 means beating 85.5% of users)", example = "85.5")
    private Double percentile;

    @Schema(description = "Gamified rank title based on percentile", example = "ELITE (Top 15%)")
    private String rankTitle;
}