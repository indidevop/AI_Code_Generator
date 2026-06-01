package com.springboot.AI_Code_Generator.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Value("${stripe.secret}")
    private String stripeSecret;

    // In Spring Boot, the @PostConstruct annotation marks a initialization method that runs
    // exactly once, immediately after the bean is instantiated and all dependency
    // injections are complete.
    @PostConstruct
    public void init(){
        // This line connects SDK with Dashboard
        Stripe.apiKey = stripeSecret;
    }


}
