package com.metabotrackapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Hypothetical parameters submitted for the CWK1 strict rule engine simulation")
public class SimulationRequestDTO {

    @Schema(description = "User ID", example = "1001")
    private Long userId;

    @Schema(description = "Target steps", example = "10000")
    private Integer targetSteps;

    @Schema(description = "Target active minutes", example = "45")
    private Integer targetActiveMinutes;

    @Schema(description = "Expected calories burned", example = "2500.0")
    private Double targetCaloriesBurned;

    @Schema(description = "Target sleep hours", example = "7.5")
    private Double targetSleepHours;

    @Schema(description = "Target hydration in liters", example = "2.5")
    private Double targetHydration;

    @Schema(description = "Current continuous exercise streak (days)", example = "5")
    private Integer currentStreak;

    @Schema(description = "Baseline resting heart rate", example = "65.0")
    private Double restingHeartRate;

    @Schema(description = "Expected average heart rate", example = "115.0")
    private Double averageHeartRate;
}