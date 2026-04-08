package com.metabotrackapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metabotrackapi.dto.SimulationRequestDTO;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.*;

import java.util.List;

public interface AnalyticsService extends IService<DailyMetabolicRecord> {
    List<EfficiencyDistributionVO> getPopulationEfficiencyDistribution();

    List<SleepQualityLeverageVO> getSleepQualityLeverage();

    List<ExerciseStreakBenefitVO> getExerciseStreakBenefit();

    UserPercentileVO getUserPercentileRank(Long userId);

    SimulationResultVO runDynamicSimulator(SimulationRequestDTO requestDTO);
}
