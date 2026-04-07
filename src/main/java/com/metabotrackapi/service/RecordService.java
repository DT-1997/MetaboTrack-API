package com.metabotrackapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metabotrackapi.dto.RecordPageQueryDTO;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.result.PageResult;

public interface RecordService extends IService<DailyMetabolicRecord> {

    /**
     * Execute paginated query for daily metabolic records.
     * @param recordPageQueryDTO Pagination parameters (pageNum, pageSize)
     * @return Formatted PageResult containing total count and data list
     */
    PageResult pageQuery(RecordPageQueryDTO recordPageQueryDTO);
}
