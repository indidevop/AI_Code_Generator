package com.springboot.AI_Code_Generator.dto.subscription;

public record PlanLimitsResponse(
        String planName,
        Integer maxLimitsPerDay,
        Integer maxProjects,
        Boolean unlimitedAi
) {
}
