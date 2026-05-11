package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.subscription.PlanResponse;

import java.util.List;

public interface PlanService {
    List<PlanResponse> getAllActivePlans();
}
