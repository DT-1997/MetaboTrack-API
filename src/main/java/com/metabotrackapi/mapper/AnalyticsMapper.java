package com.metabotrackapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
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
}
