package com.springboot.AI_Code_Generator.dto.subscription;

public record PlanResponse(
        Long id,
        String name,
        Integer maxProjects,
        Integer maxTokensPerDay,
        Integer maxPreviews, //max number of previews allowed per plan
        Boolean unlimitedAi
) {
}
