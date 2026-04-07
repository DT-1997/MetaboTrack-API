package com.metabotrackapi.dto;

import com.metabotrackapi.enumeration.CalorieEfficiencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for updating an existing metabolic record")
public class RecordUpdateDTO {

    @Schema(description = "Corrected total steps taken in the day", example = "13000")
    private Integer stepsPerDay;

    @Schema(description = "Corrected high-intensity active minutes", example = "50")
    private Integer activeMinutes;

    @Schema(description = "Corrected calories burned", example = "700.0")
    private Double caloriesBurned;

    @Schema(description = "Corrected hours of sleep", example = "8.0")
    private Double sleepHours;

    @Schema(description = "Corrected water intake in liters", example = "3.0")
    private Double hydrationLiters;

    @Schema(description = "Corrected resting heart rate", example = "60.0")
    private Double heartRateResting;

    @Schema(description = "Corrected average heart rate", example = "110.0")
    private Double heartRateAvg;

    @Schema(description = "Corrected continuous exercise streak", example = "6")
    private Integer continuousExerciseDays;

    @Schema(description = "Recalculated efficiency raw score", example = "9.2")
    private Double efficiencyScore;

    @Schema(description = "Recalculated categorical efficiency rating", example = "HIGH")
    private CalorieEfficiencyEnum calorieEfficiency;
}