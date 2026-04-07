package com.metabotrackapi.controller;

import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.result.Result;
import com.metabotrackapi.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
@Tag(name = "Record Management", description = "Standardized endpoints for managing daily metabolic and activity data snapshots.")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Record Details", description = "Query detailed information based on the specified record ID.")
    public Result<DailyMetabolicRecord> getRecordById(
            @Parameter(description = "Unique identifier of the metabolic record") @PathVariable Long id
    ) {
        DailyMetabolicRecord record = recordService.getById(id);
        if (record == null) {
            return Result.error(404, "Record Not Found");
        }
        return Result.success(record);
    }


}
