package com.metabotrackapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metabotrackapi.constant.RankConstant;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.mapper.AnalyticsMapper;
import com.metabotrackapi.service.AnalyticsService;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
import com.metabotrackapi.vo.ExerciseStreakBenefitVO;
import com.metabotrackapi.vo.SleepQualityLeverageVO;
import com.metabotrackapi.vo.UserPercentileVO;
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

    @Override
    public List<SleepQualityLeverageVO> getSleepQualityLeverage() {
        return analyticsMapper.getSleepQualityLeverage();
    }

    @Override
    public List<ExerciseStreakBenefitVO> getExerciseStreakBenefit() {
        return analyticsMapper.getExerciseStreakBenefit();
    }

    @Override
    public UserPercentileVO getUserPercentileRank(Long userId) {

        Double personalAvg = analyticsMapper.getUserAvgEfficiency(userId);

        if (personalAvg == null) {
            return UserPercentileVO.builder()
                    .userId(userId)
                    .personalAvgScore(0.0)
                    .percentile(0.0)
                    .rankTitle(RankConstant.UNRANKED)
                    .build();
        }

        Long totalUsers = analyticsMapper.getTotalUserCount();
        Long usersBelow = analyticsMapper.countUsersBelowScore(personalAvg);

        // Prevent division by zero in case of an empty database (though highly unlikely here)
        double percentile = totalUsers > 0 ? ((double) usersBelow / totalUsers) * 100 : 0.0;

        // Round to 1 decimal place (e.g., 85.5%)
        percentile = Math.round(percentile * 10.0) / 10.0;

        String rankTitle;
        if (percentile >= 90.0) {
            rankTitle = RankConstant.MASTER;
        } else if (percentile >= 75.0) {
            rankTitle = RankConstant.ELITE;
        } else if (percentile >= 50.0) {
            rankTitle = RankConstant.ADVANCED;
        } else {
            rankTitle = RankConstant.RISING_STAR;
        }

        return UserPercentileVO.builder()
                .userId(userId)
                .personalAvgScore(personalAvg)
                .percentile(percentile)
                .rankTitle(rankTitle)
                .build();

    }
}
