package com.springboot.AI_Code_Generator.controller;

import com.springboot.AI_Code_Generator.dto.subscription.*;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.PlanService;
import com.springboot.AI_Code_Generator.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BillingController {
    private final PlanService planService;
    private final SubscriptionService subscriptionService;
    private final AuthUtil authUtil;

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans(){
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/api/me/subscription")
    public ResponseEntity<SubscriptionResponse> getMySubscription(){
        Long userId=authUtil.getCurrentUserId();
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription(userId));
    }

    @PostMapping("/api/stripe/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutResponse(@RequestBody CheckoutRequest request){
        Long userId=authUtil.getCurrentUserId();
        return ResponseEntity.ok(subscriptionService.createCheckoutSessionUrl(request,userId));
    }

    @PostMapping("/api/stripe/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal(){
        Long userId = authUtil.getCurrentUserId();
        return ResponseEntity.ok(subscriptionService.openCustomerPortal(userId));
    }
}
