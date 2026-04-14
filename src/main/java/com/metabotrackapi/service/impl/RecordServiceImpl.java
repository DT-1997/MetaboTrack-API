package com.metabotrackapi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metabotrackapi.converter.RecordConverter;
import com.metabotrackapi.dto.RecordPageQueryDTO;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.exception.BusinessException;
import com.metabotrackapi.mapper.RecordMapper;
import com.metabotrackapi.result.PageResult;
import com.metabotrackapi.service.RecordService;
import com.metabotrackapi.util.UserContext;
import com.metabotrackapi.vo.RecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper, DailyMetabolicRecord> implements RecordService {

    private final RecordConverter recordConverter;

    @Override
    public PageResult pageQuery(RecordPageQueryDTO recordPageQueryDTO) {
        Page<DailyMetabolicRecord> page = new Page<>(recordPageQueryDTO.getPageNum(), recordPageQueryDTO.getPageSize());

        Page<DailyMetabolicRecord> resultPage = lambdaQuery()
                .eq(recordPageQueryDTO.getUserId() != null, DailyMetabolicRecord::getUserId, recordPageQueryDTO.getUserId())
                .eq(recordPageQueryDTO.getCalorieEfficiency() != null, DailyMetabolicRecord::getCalorieEfficiency, recordPageQueryDTO.getCalorieEfficiency())
                .orderByDesc(DailyMetabolicRecord::getEfficiencyScore)
                .page(page);

        long total = resultPage.getTotal();

        List<DailyMetabolicRecord> records = resultPage.getRecords();

        List<RecordVO> voList = records.stream()
                .map(recordConverter::toVO)
                .toList();

        return new PageResult(total, voList);
    }

    @Override
    public boolean updateRecordWithAuth(DailyMetabolicRecord record) {
        // 1. Fetch existing data
        DailyMetabolicRecord existing = this.getById(record.getId());

        if (existing == null) {
            throw new BusinessException("Update failed: Record Not Found");
        }

        // 2. Ownership check
        if (!existing.getUserId().equals(UserContext.getCurrentId())) {
            throw new BusinessException("Access Denied: You cannot modify records belonging to other users.");
        }

        // 3. Execute update
        return updateById(record);
    }

    @Override
    public boolean deleteRecordsWithAuth(List<Long> ids) {
        // 1. Query the records to check their owners
        List<DailyMetabolicRecord> records = this.listByIds(ids);

        if (records.size() < ids.size()) {
            throw new BusinessException("Delete failed: Some records do not exist.");
        }

        // 2. Check if all records belong to the current user
        Long currentUserId = UserContext.getCurrentId();
        boolean unauthorized = records.stream()
                .anyMatch(r -> !r.getUserId().equals(currentUserId));

        if (unauthorized) {
            throw new BusinessException("Access Denied: One or more records do not belong to you.");
        }

        // 3. Perform batch delete
        return removeByIds(ids);
    }
}
