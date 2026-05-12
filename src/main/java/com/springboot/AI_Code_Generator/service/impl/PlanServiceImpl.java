package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.subscription.PlanResponse;
import com.springboot.AI_Code_Generator.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
