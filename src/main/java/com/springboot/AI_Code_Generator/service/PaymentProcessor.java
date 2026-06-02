package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.subscription.CheckoutRequest;
import com.springboot.AI_Code_Generator.dto.subscription.CheckoutResponse;
import com.springboot.AI_Code_Generator.dto.subscription.PortalResponse;
import com.stripe.model.StripeObject;

import java.util.Map;

// Why another interface for payment?
// If we have multiple payment processors, this will segregate the requests accordingly
public interface PaymentProcessor {
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal(Long userId);

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);
}
