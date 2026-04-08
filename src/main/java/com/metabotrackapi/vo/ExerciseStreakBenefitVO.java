package com.metabotrackapi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Aggregated data quantifying the physiological benefits of continuous exercise streaks")
public class ExerciseStreakBenefitVO {

    @Schema(description = "Categorized exercise streak range", example = "CONSISTENT (4-7 Days)")
    private String streakCategory;

    @Schema(description = "Number of records in this streak category", example = "3200")
    private Long recordCount;

    @Schema(description = "Average resting heart rate (bpm) - lower values indicate better cardiovascular health", example = "64.2")
    private Double avgRestingHeartRate;

    @Schema(description = "Average efficiency score - higher values indicate better metabolic performance", example = "8.9")
    private Double avgEfficiencyScore;

    @Schema(description = "Average calories burned per day for this group", example = "580.0")
    private Double avgCaloriesBurned;
}