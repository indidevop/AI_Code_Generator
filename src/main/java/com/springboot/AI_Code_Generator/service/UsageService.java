package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.subscription.PlanLimitsResponse;
import com.springboot.AI_Code_Generator.dto.subscription.UsageTodayResponse;

public interface UsageService {
    UsageTodayResponse getUsageForToday(Long userId);

    PlanLimitsResponse getPlanLimits(Long userId);
}
