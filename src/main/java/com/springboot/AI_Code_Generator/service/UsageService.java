package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.subscription.PlanLimitsResponse;
import com.springboot.AI_Code_Generator.dto.subscription.UsageTodayResponse;

public interface UsageService {
    void recordTokenUsage(Long userId, int actualTokens);
    Integer checkDailyTokensUsage();
}



