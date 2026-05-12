package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.subscription.PlanLimitsResponse;
import com.springboot.AI_Code_Generator.dto.subscription.UsageTodayResponse;
import com.springboot.AI_Code_Generator.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService {
    @Override
    public UsageTodayResponse getUsageForToday(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getPlanLimits(Long userId) {
        return null;
    }
}
