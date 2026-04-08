package com.metabotrackapi.controller;

import com.metabotrackapi.result.Result;
import com.metabotrackapi.service.AnalyticsService;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
import com.metabotrackapi.vo.SleepQualityLeverageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Advanced Analytics (Insights)", description = "High-level data aggregation and predictive analytics endpoints based on population snapshots.")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/efficiency-distribution")
    @Operation(
            summary = "Get Population Efficiency Distribution",
            description = "Aggregates the 15,000-user snapshot dataset to calculate the distribution of efficiency tiers. Returns user counts and average physiological metrics per tier (LOW, MODERATE, HIGH) to support dashboard visualizations."
    )
    public Result<List<EfficiencyDistributionVO>> getEfficiencyDistribution() {
        List<EfficiencyDistributionVO> distributionList = analyticsService.getPopulationEfficiencyDistribution();
        return Result.success(distributionList);
    }

    @GetMapping("/sleep-quality")
    @Operation(
            summary = "Analyze Sleep Quality Leverage",
            description = "Groups the population into sleep duration buckets (<6h, 6-8h, >8h) and calculates average exercise volume vs. average heart rate. Used to medically prove that insufficient sleep increases cardiovascular burden for the same amount of exercise."
    )
    public Result<List<SleepQualityLeverageVO>> getSleepQualityLeverage() {
        List<SleepQualityLeverageVO> leverageList = analyticsService.getSleepQualityLeverage();
        return Result.success(leverageList);
    }

}
