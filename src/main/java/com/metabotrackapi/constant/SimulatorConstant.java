package com.metabotrackapi.constant;

import lombok.Data;

@Data
public class SimulatorConstant {

    public static final String STREAK_BONUS = "⭐ +10% Jump Bonus applied for crossing 5-day streak!";

    public static final String INSUFFICIENT_SLEEP_PENALTY = "⚠️ -10% Penalty: High consistency but insufficient recovery (sleep < 6h).";

    public static final String OVER_TRAINING_PENALTY = "🛑 -15% Drop: Over-training detected (>6 days without rest).";

    public static final String SEVERE_SLEEP_PENALTY = "🚨 -25% Drop: Severe sleep deprivation (<5h).";

}
