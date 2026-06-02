package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.subscription.CheckoutRequest;
import com.springboot.AI_Code_Generator.dto.subscription.CheckoutResponse;
import com.springboot.AI_Code_Generator.dto.subscription.PortalResponse;
import com.springboot.AI_Code_Generator.entity.Plan;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.error.ResourceNotFoundException;
import com.springboot.AI_Code_Generator.repository.PlanRepository;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripePaymentProcessorImpl implements PaymentProcessor {

    private final AuthUtil authUtil;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Value("${CLIENT_URL}")
    private String frontend_domain;

    // This method generates a link using which user can pay
    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {

        try {
        Long currentUserId = authUtil.getCurrentUserId();
        User customer = userRepository.findById(currentUserId).orElseThrow(()->{
            return new ResourceNotFoundException("user",currentUserId.toString());
        });
        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(()-> new ResourceNotFoundException("Plan",request.planId().toString()));

        var params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontend_domain + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontend_domain + "/cancel.html")
                .putMetadata("userId",currentUserId.toString())
                .putMetadata("planId",plan.getId().toString());

            // If customer is not first time payer this will tell stripe to not create new customer id
            String stripeCustomerId = customer.getStripeCustomerId();

            if(stripeCustomerId == null || stripeCustomerId.isEmpty())
            {
                params.setCustomerEmail(customer.getUsername());
            }else{
                params.setCustomer(stripeCustomerId);
            }

            // SDK will call API
            Session session = Session.create(params.build());

            return new CheckoutResponse(session.getUrl());

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
