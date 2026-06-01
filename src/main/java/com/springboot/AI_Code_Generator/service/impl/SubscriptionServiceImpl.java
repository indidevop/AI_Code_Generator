package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.subscription.CheckoutRequest;
import com.springboot.AI_Code_Generator.dto.subscription.CheckoutResponse;
import com.springboot.AI_Code_Generator.dto.subscription.PortalResponse;
import com.springboot.AI_Code_Generator.dto.subscription.SubscriptionResponse;
import com.springboot.AI_Code_Generator.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }
}
