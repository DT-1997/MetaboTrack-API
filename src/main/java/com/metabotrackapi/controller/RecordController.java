package com.metabotrackapi.controller;

import com.metabotrackapi.converter.RecordConverter;
import com.metabotrackapi.dto.RecordCreateDTO;
import com.metabotrackapi.dto.RecordPageQueryDTO;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.result.PageResult;
import com.metabotrackapi.result.Result;
import com.metabotrackapi.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
@Tag(name = "Record Management", description = "Standardized endpoints for managing daily metabolic and activity data snapshots.")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordConverter recordConverter;

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

    @GetMapping
    @Operation(summary = "Get Paginated Records", description = "Retrieve a paginated list of metabolic records using DTO parameters.")
    public Result<PageResult> getRecords(@ParameterObject RecordPageQueryDTO recordPageQueryDTO) {
        PageResult pageResult = recordService.pageQuery(recordPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "Create Daily Metabolic Record", description = "Receives and saves a daily metabolic record of the user's metabolic metrics (e.g., exercise, heart rate, sleep).")
    public Result<Boolean> createRecord(@RequestBody RecordCreateDTO recordCreateDTO) {
        boolean success = recordService.save(recordConverter.toEntity(recordCreateDTO));
        return success ? Result.success(true) : Result.error("Failed to create record");
    }


}
