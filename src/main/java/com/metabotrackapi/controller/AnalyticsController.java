package com.metabotrackapi.controller;

import com.metabotrackapi.dto.SimulationRequestDTO;
import com.metabotrackapi.result.Result;
import com.metabotrackapi.service.AnalyticsService;
import com.metabotrackapi.util.UserContext;
import com.metabotrackapi.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "3. Advanced Analytics (Insights)", description = "High-level data aggregation and predictive analytics endpoints based on population snapshots.")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/population/efficiency-distribution")
    @Operation(
            summary = "Get Population Efficiency Distribution",
            description = "Aggregates the 15,000-user snapshot dataset to calculate the distribution of efficiency tiers. Returns user counts and average physiological metrics per tier (LOW, MODERATE, HIGH) to support dashboard visualizations."
    )
    public Result<List<EfficiencyDistributionVO>> getEfficiencyDistribution() {
        List<EfficiencyDistributionVO> distributionData = analyticsService.getPopulationEfficiencyDistribution();
        return Result.success(distributionData);
    }

    @GetMapping("/leverage/sleep-quality")
    @Operation(
            summary = "Analyze Sleep Quality Leverage",
            description = "Groups the population into sleep duration buckets (<6h, 6-8h, >8h) and calculates average exercise volume vs. average heart rate. Used to medically prove that insufficient sleep increases cardiovascular burden for the same amount of exercise."
    )
    public Result<List<SleepQualityLeverageVO>> getSleepQualityLeverage() {
        List<SleepQualityLeverageVO> leverageData = analyticsService.getSleepQualityLeverage();
        return Result.success(leverageData);
    }

    @GetMapping("/leverage/exercise-streak")
    @Operation(
            summary = "Analyze Exercise Streak Benefits",
            description = "Groups the dataset by continuous exercise days (0, 1-3, 4-7, >7) to demonstrate how habits affect resting heart rate and metabolic efficiency scores. High-value insight for behavioral health analysis."
    )
    public Result<List<ExerciseStreakBenefitVO>> getExerciseStreakBenefit() {
        List<ExerciseStreakBenefitVO> streakData = analyticsService.getExerciseStreakBenefit();
        return Result.success(streakData);
    }

    @GetMapping("/users/{userId}/percentile")
    @Operation(
            summary = "Individual Percentile Ranking",
            description = "Calculates where a specific user stands compared to the entire population. It aggregates the user's historical efficiency score and computes their percentile rank against all other users in the database, returning a gamified title."
    )
    public Result<UserPercentileVO> getUserPercentile(
            @Parameter(example = "1001") @PathVariable Long userId) {
        Long currentUserId = UserContext.getCurrentId();

        if (!userId.equals(currentUserId)) {
            return Result.error(403, "Access Denied: You do not have permission to view analytics for other users.");
        }

        UserPercentileVO rankData = analyticsService.getUserPercentileRank(userId);
        return Result.success(rankData);
    }

    @PostMapping("/simulator")
    @Operation(
            summary = "Dynamic Target Predictor & Simulator",
            description = "A rule-based engine that accepts hypothetical daily targets (e.g., planned sleep, planned steps) and predicts the user's future efficiency score. Used for frontend interactive sliders and goal setting."
    )
    public Result<SimulationResultVO> runSimulator(@RequestBody SimulationRequestDTO requestDTO) {
        SimulationResultVO result = analyticsService.runDynamicSimulator(requestDTO);
        return Result.success(result);
    }


}
