package com.metabotrackapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
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
}
