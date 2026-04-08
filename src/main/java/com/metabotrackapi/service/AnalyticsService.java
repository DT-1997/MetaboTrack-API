package com.metabotrackapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
import com.metabotrackapi.vo.ExerciseStreakBenefitVO;
import com.metabotrackapi.vo.SleepQualityLeverageVO;
import com.metabotrackapi.vo.UserPercentileVO;

import java.util.List;

public interface AnalyticsService extends IService<DailyMetabolicRecord> {
    List<EfficiencyDistributionVO> getPopulationEfficiencyDistribution();

    List<SleepQualityLeverageVO> getSleepQualityLeverage();

    List<ExerciseStreakBenefitVO> getExerciseStreakBenefit();

    UserPercentileVO getUserPercentileRank(Long userId);
}
