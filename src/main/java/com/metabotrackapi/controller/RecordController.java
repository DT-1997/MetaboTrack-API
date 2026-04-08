package com.metabotrackapi.controller;

import com.metabotrackapi.converter.RecordConverter;
import com.metabotrackapi.dto.RecordCreateDTO;
import com.metabotrackapi.dto.RecordPageQueryDTO;
import com.metabotrackapi.dto.RecordUpdateDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
@Tag(name = "Record Management", description = "Standardized endpoints for managing daily metabolic and activity data snapshots.")
public class RecordController {

    private final RecordService recordService;

    private final RecordConverter recordConverter;

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

    @PutMapping("/{id}")
    @Operation(summary = "Update Anomalous Record Data", description = "Correct previously entered dynamic data (e.g., step count errors due to wearable sync delays).")
    public Result<Boolean> updateRecord(
            @Parameter(description = "ID of the record to be modified") @PathVariable Long id,
            @RequestBody RecordUpdateDTO recordUpdateDTO) {

        DailyMetabolicRecord recordToUpdate = recordConverter.toEntity(recordUpdateDTO);

        recordToUpdate.setId(id);

        boolean success = recordService.updateById(recordToUpdate);

        return success ? Result.success(true) : Result.error("Failed to update record");
    }

    @DeleteMapping
    @Operation(summary = "Batch Deletion of Records", description = "Hard delete multiple extreme anomalous data records simultaneously. Expects a JSON array of record IDs in the request body.")
    public Result<Boolean> deleteRecords(
            @Parameter(description = "List of record IDs to be deleted")
            @RequestBody List<Long> ids
    ) {
        if (ids == null || ids.isEmpty()) {
            return Result.error(400, "The list of IDs to delete cannot be empty.");
        }

        boolean success = recordService.removeByIds(ids);

        return success ? Result.success(true) : Result.error("Failed to delete records. Some IDs might not exist.");
    }

}
