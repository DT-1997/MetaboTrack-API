package com.metabotrackapi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metabotrackapi.converter.RecordConverter;
import com.metabotrackapi.dto.RecordPageQueryDTO;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.mapper.RecordMapper;
import com.metabotrackapi.result.PageResult;
import com.metabotrackapi.service.RecordService;
import com.metabotrackapi.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, DailyMetabolicRecord> implements RecordService {

    @Autowired
    private RecordConverter recordConverter;

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
                .map(record -> recordConverter.toVO(record))
                .toList();

        return new PageResult(total, voList);
    }
}
