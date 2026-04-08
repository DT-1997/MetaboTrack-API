package com.metabotrackapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metabotrackapi.constant.RankConstant;
import com.metabotrackapi.constant.SimulatorConstant;
import com.metabotrackapi.dto.SimulationRequestDTO;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.enumeration.CalorieEfficiencyEnum;
import com.metabotrackapi.mapper.AnalyticsMapper;
import com.metabotrackapi.service.AnalyticsService;
import com.metabotrackapi.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public SimulationResultVO runDynamicSimulator(SimulationRequestDTO req) {

        List<String> feedback = new ArrayList<>();

        // 1. The Base Signal
        // efficiency = calories_burned / (steps_per_day + 20 * active_minutes)
        double denominator = req.getTargetSteps() + (20.0 * req.getTargetActiveMinutes());
        double baseSignal = denominator > 0 ? (req.getTargetCaloriesBurned() / denominator) * 100.0 : 0.0;
        double score = baseSignal;
        feedback.add("Base signal calculated from movement-to-calorie ratio.");

        // 2. Linear Weights
        // Hydration: +0.05 weight | Sleep: +0.05 weight
        score += (req.getTargetHydration() * 0.05);
        score += (req.getTargetSleepHours() * 0.05);

        // 3. Heart Rate Scalers (From Image 3: Heart Rate Doesn't Stay Silent)
        // Scales using 80 / resting_hr AND 120 / average_hr
        if (req.getRestingHeartRate() > 0) {
            score *= (80.0 / req.getRestingHeartRate());
        }
        if (req.getAverageHeartRate() > 0) {
            score *= (120.0 / req.getAverageHeartRate());
        }

        // 4. Consistency Multipliers & Penalties
        int streak = req.getCurrentStreak();

        // Each extra day adds +3% improvement
        double streakBonus = streak * 0.03;
        score *= (1.0 + streakBonus);

        // Crossing 5 days triggers a sudden +10% jump
        if (streak >= 5) {
            score *= 1.10;
            feedback.add(SimulatorConstant.STREAK_BONUS);
        }

        // Push too far without recovery: If streak >= 6 AND sleep < 6 hours -> -10% penalty
        if (streak >= 6 && req.getTargetSleepHours() < 6.0) {
            score *= 0.90;
            feedback.add(SimulatorConstant.INSUFFICIENT_SLEEP_PENALTY);
        }

        // 5. Hard Breakdowns
        // Workouts > 6/week -> drop by 15%
        if (streak > 6) {
            score *= 0.85;
            feedback.add(SimulatorConstant.OVER_TRAINING_PENALTY);
        }
        // Sleep < 5 hours -> drop by 25%
        if (req.getTargetSleepHours() < 5.0) {
            score *= 0.75;
            feedback.add(SimulatorConstant.SEVERE_SLEEP_PENALTY);
        }

        // 6. Squeeze to 0-10 Range
        score = Math.max(0.0, Math.min(10.0, score));
        score = Math.round(score * 10.0) / 10.0;

        // 7. Determine Tier based on FIXED cutoffs
        CalorieEfficiencyEnum projectedTier;
        if (score >= 7.0) {
            projectedTier = CalorieEfficiencyEnum.HIGH;
        } else if (score >= 4.0) {
            projectedTier = CalorieEfficiencyEnum.MODERATE;
        } else {
            projectedTier = CalorieEfficiencyEnum.LOW;
        }

        return SimulationResultVO.builder()
                .baselineScore(0.0)
                .projectedScore(score)
                .scoreChange("Calculated from scratch")
                .projectedTier(projectedTier)
                .feedbackMessages(feedback)
                .build();
    }
}
