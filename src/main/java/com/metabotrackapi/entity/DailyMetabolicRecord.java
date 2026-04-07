package com.metabotrackapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("daily_metabolic_record")
public class DailyMetabolicRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
