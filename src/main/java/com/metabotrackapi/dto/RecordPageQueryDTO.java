package com.metabotrackapi.dto;

import com.metabotrackapi.enumeration.CalorieEfficiencyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Pagination Query DTO for Metabolic Records")
public class RecordPageQueryDTO {

    @Schema(description = "Current page number", example = "1")
    private int pageNum = 1;

    @Schema(description = "Number of records per page", example = "10")
    private int pageSize = 10;

    @Schema(description = "Filter by Login in User ID")
    private Long userId;

    @Schema(description = "Filter by specific calorie efficiency rating (1=LOW, 2=MODERATE, 3=HIGH)")
    private CalorieEfficiencyEnum calorieEfficiency;

}
