package com.metabotrackapi.enumeration;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CalorieEfficiencyEnum {

    LOW(1, "Low Efficiency"),
    MODERATE(2, "Moderate"),
    HIGH(3, "High Efficiency");

    @EnumValue
    private final int code;

    @JsonValue
    private final String description;

    CalorieEfficiencyEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
