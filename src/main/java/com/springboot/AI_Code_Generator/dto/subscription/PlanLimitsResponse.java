package com.springboot.AI_Code_Generator.dto.subscription;

public record PlanLimitsResponse(
        String planName,
        int maxLimitsPerDay,
        int maxProjects,
        boolean unlimitedAi
) {
}
