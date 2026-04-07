package com.metabotrackapi.dto;

import com.metabotrackapi.enumeration.CalorieEfficiencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Data Transfer Object for creating a new daily metabolic record snapshot")
public class RecordCreateDTO {

    @Schema(description = "Unique identifier of the user", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "The specific date this record belongs to", example = "2026-04-07", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate recordDate;

    @Schema(description = "Total steps taken in the day", example = "12500")
    private Integer stepsPerDay;

    @Schema(description = "Total high-intensity active minutes", example = "45")
    private Integer activeMinutes;

    @Schema(description = "Total calories burned", example = "650.5")
    private Double caloriesBurned;

    @Schema(description = "Hours of sleep from the previous night", example = "7.5")
    private Double sleepHours;

    @Schema(description = "Total water intake in liters", example = "2.5")
    private Double hydrationLiters;

    @Schema(description = "Resting heart rate in bpm", example = "62.5")
    private Double heartRateResting;

    @Schema(description = "Average daily heart rate in bpm", example = "115.0")
    private Double heartRateAvg;

    @Schema(description = "Current streak of continuous exercise days", example = "5")
    private Integer continuousExerciseDays;

    @Schema(description = "Calculated efficiency raw score", example = "8.75")
    private Double efficiencyScore;

    @Schema(description = "Categorical efficiency rating (1=LOW, 2=MODERATE, 3=HIGH)", example = "HIGH")
    private CalorieEfficiencyEnum calorieEfficiency;
}