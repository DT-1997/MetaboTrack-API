package com.metabotrackapi.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordVO {
    private Long userId;

    private LocalDate recordDate;

    private Integer stepsPerDay;

    private Integer activeMinutes;

    private Double caloriesBurned;

    private Double sleepHours;

    private Double hydrationLiters;

    private Double heartRateResting;

    private Double heartRateAvg;

    private Integer continuousExerciseDays;

    private Double efficiencyScore;

    private Short calorieEfficiency;
}
