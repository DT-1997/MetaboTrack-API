package com.metabotrackapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.EfficiencyDistributionVO;

import java.util.List;

public interface AnalyticsService extends IService<DailyMetabolicRecord> {
    List<EfficiencyDistributionVO> getPopulationEfficiencyDistribution();
}
