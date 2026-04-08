package com.metabotrackapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.mapper.AnalyticsMapper;
import com.metabotrackapi.service.AnalyticsService;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl extends ServiceImpl<AnalyticsMapper, DailyMetabolicRecord> implements AnalyticsService {

    private final AnalyticsMapper analyticsMapper;

    @Override
    public List<EfficiencyDistributionVO> getPopulationEfficiencyDistribution() {
        return analyticsMapper.getEfficiencyDistribution();
    }
}
