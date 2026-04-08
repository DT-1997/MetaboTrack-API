package com.metabotrackapi.vo;

import com.metabotrackapi.enumeration.CalorieEfficiencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Aggregated data representing the macro distribution of metabolic efficiency tiers")
public class EfficiencyDistributionVO {

    @Schema(description = "The efficiency tier (LOW, MODERATE, HIGH)", example = "HIGH")
    private CalorieEfficiencyEnum tier;

    @Schema(description = "Total number of users falling into this tier", example = "4520")
    private Long userCount;

    @Schema(description = "Average daily steps for users in this tier", example = "12450")
    private Integer avgSteps;

    @Schema(description = "Average active minutes for users in this tier", example = "55.5")
    private Double avgActiveMinutes;

    @Schema(description = "Average sleep hours for users in this tier", example = "7.8")
    private Double avgSleepHours;

    @Schema(description = "Average resting heart rate for users in this tier", example = "62.3")
    private Double avgRestingHeartRate;
}
