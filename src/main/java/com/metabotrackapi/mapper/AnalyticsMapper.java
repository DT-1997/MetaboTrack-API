package com.metabotrackapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
import com.metabotrackapi.vo.ExerciseStreakBenefitVO;
import com.metabotrackapi.vo.SleepQualityLeverageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnalyticsMapper extends BaseMapper<DailyMetabolicRecord> {

    @Select("SELECT " +
            "calorie_efficiency AS tier," +
            "COUNT(id) AS userCount," +
            "ROUND(AVG(steps_per_day), 0) AS avgSteps," +
            "ROUND(AVG(active_minutes), 1) AS avgActiveMinutes," +
            "ROUND(AVG(sleep_hours), 1) AS avgSleepHours, " +
            "ROUND(AVG(heart_rate_resting), 1) AS avgRestingHeartRate " +
            "FROM daily_metabolic_record " +
            "GROUP BY calorie_efficiency")
    List<EfficiencyDistributionVO> getEfficiencyDistribution();

    @Select("SELECT " +
            "CASE " +
            "   WHEN sleep_hours < 6 THEN 'INSUFFICIENT (<6h)'" +
            "   WHEN sleep_hours >= 6 AND sleep_hours <= 8 THEN 'NORMAL (6-8h)'" +
            "   ELSE 'SUFFICIENT (>8h)' " +
            "END AS sleepCategory, " +
            "COUNT(id) AS userCount, " +
            "ROUND(AVG(steps_per_day), 0) AS avgSteps, " +
            "ROUND(AVG(heart_rate_resting), 1) AS avgRestingHeartRate, " +
            "ROUND(AVG(heart_rate_avg), 1) AS avgDailyHeartRate " +
            "FROM daily_metabolic_record " +
            "GROUP BY sleepCategory " +
            "ORDER BY " +
            "  CASE sleepCategory " +
            "    WHEN 'INSUFFICIENT (<6h)' THEN 1 " +
            "    WHEN 'NORMAL (6-8h)' THEN 2 " +
            "    ELSE 3 " +
            "  END")
    List<SleepQualityLeverageVO> getSleepQualityLeverage();

    @Select("SELECT " +
            "  CASE " +
            "    WHEN continuous_exercise_days = 0 THEN '01. NO STREAK (0 Days)' " +
            "    WHEN continuous_exercise_days BETWEEN 1 AND 3 THEN '02. SHORT STREAK (1-3 Days)' " +
            "    WHEN continuous_exercise_days BETWEEN 4 AND 7 THEN '03. CONSISTENT (4-7 Days)' " +
            "    ELSE '04. ELITE STREAK (>7 Days)' " +
            "  END AS streakCategory, " +
            "  COUNT(id) AS recordCount, " +
            "  ROUND(AVG(heart_rate_resting), 1) AS avgRestingHeartRate, " +
            "  ROUND(AVG(efficiency_score), 2) AS avgEfficiencyScore, " +
            "  ROUND(AVG(calories_burned), 1) AS avgCaloriesBurned " +
            "FROM daily_metabolic_record " +
            "GROUP BY streakCategory " +
            "ORDER BY streakCategory ASC")
    List<ExerciseStreakBenefitVO> getExerciseStreakBenefit();
}
