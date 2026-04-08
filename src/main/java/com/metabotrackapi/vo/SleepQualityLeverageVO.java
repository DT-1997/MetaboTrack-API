package com.metabotrackapi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Aggregated data demonstrating the relationship between sleep duration and cardiovascular burden")
public class SleepQualityLeverageVO {

    @Schema(description = "Categorized sleep duration bucket", example = "NORMAL (6-8h)")
    private String sleepCategory;

    @Schema(description = "Total number of users in this sleep category", example = "8500")
    private Long userCount;

    @Schema(description = "Average daily steps taken by users in this category", example = "10500")
    private Integer avgSteps;

    @Schema(description = "Average resting heart rate (bpm) - higher means more cardiovascular stress", example = "68.5")
    private Double avgRestingHeartRate;

    @Schema(description = "Average daily heart rate (bpm) during activities", example = "115.2")
    private Double avgDailyHeartRate;
}
