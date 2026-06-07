package com.springboot.AI_Code_Generator.mapper;

import com.springboot.AI_Code_Generator.dto.subscription.PlanResponse;
import com.springboot.AI_Code_Generator.dto.subscription.SubscriptionResponse;
import com.springboot.AI_Code_Generator.entity.Plan;
import com.springboot.AI_Code_Generator.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);
}
