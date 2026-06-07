package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.subscription.CheckoutRequest;
import com.springboot.AI_Code_Generator.dto.subscription.CheckoutResponse;
import com.springboot.AI_Code_Generator.dto.subscription.PortalResponse;
import com.springboot.AI_Code_Generator.entity.Plan;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.enums.SubscriptionStatus;
import com.springboot.AI_Code_Generator.error.BadRequestException;
import com.springboot.AI_Code_Generator.error.ResourceNotFoundException;
import com.springboot.AI_Code_Generator.repository.PlanRepository;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.PaymentProcessor;
import com.springboot.AI_Code_Generator.service.SubscriptionService;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;




import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentProcessorImpl implements PaymentProcessor {

    private final AuthUtil authUtil;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;

    @Value("${CLIENT_URL}")
    private String frontend_domain;

    // This method generates a link using which user can pay
    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {

        try {
        Long currentUserId = authUtil.getCurrentUserId();
            User customer = getUser(currentUserId);
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
                // this information can be retrieved from the events for further use
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
    public PortalResponse openCustomerPortal() {

        Long userId = authUtil.getCurrentUserId();
        User user = getUser(userId);

        // Create portal session
        String stripeCustomerId = user.getStripeCustomerId();

        if(stripeCustomerId == null || stripeCustomerId.isEmpty()) {
            throw new BadRequestException("User does not have a Stripe Customer Id, UserId:"+userId);
        }

        try {
            var portalSession = com.stripe.model.billingportal.Session.create(
                    com.stripe.param.billingportal.SessionCreateParams.builder()
                            .setCustomer(stripeCustomerId)
                            .setReturnUrl(frontend_domain)
                            .build()
            );

            return new PortalResponse(portalSession.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {
        log.debug("Handling stripe event: {}", type);

        switch(type){
            case "checkout.session.completed": 
                handleCheckoutSessionCompleted((Session) stripeObject,metadata); // onetime event, when checkout is completed
                break;
            case "customer.subscription.updated": 
                handleCustomerSubscriptionUpdated((Subscription) stripeObject); // if user cancels/updates
                break;
            case "customer.subscription.deleted": 
                handleCustomerSubscriptionDeleted((Subscription) stripeObject); // if subscription ends, revoke
                break;
            case "invoice_payment.paid": 
                handleInvoicePaid(((InvoicePayment) stripeObject).getInvoice()); // if invoice was paid
                break;
            case "invoice.payment_failed": 
                handleInvoicePaymentFailed(((InvoicePayment) stripeObject).getInvoice()); // if invoice payment failed, marked PAST_DUE
                break;
            default: 
                log.debug("Ignoring stripe event: {}", type);
        }


    }

    private void handleCheckoutSessionCompleted(Session session, Map<String, String> metadata){

        if(session==null)
        {
            log.error("Session object is null");
            return;
        }
        Long userId = Long.parseLong(metadata.get("userId"));
        Long planId = Long.parseLong(metadata.get("planId"));

        String subscriptionId = session.getSubscription();
        String customerId = session.getCustomer();

        User user = getUser(userId);

        if(user.getStripeCustomerId()==null)
        {
            user.setStripeCustomerId(customerId);
            userRepository.save(user);
        }

        subscriptionService.activateSubscription(userId, planId, subscriptionId, customerId);

    }

    private void handleCustomerSubscriptionUpdated(Subscription subscription){
        if(subscription==null)
        {
            log.error("Subscription object is null");
            return;
        }

        SubscriptionStatus subscriptionStatus = mapStripeStatusToEnum(subscription.getStatus());

        if(subscriptionStatus==null)
        {
            log.warn("Unknown subscription status {} for subscription id {}",subscriptionStatus,subscription.getId());
            return;
        }

        SubscriptionItem item = subscription.getItems().getData().get(0);
        Instant periodStart = toInstant(item.getCurrentPeriodStart());
        Instant periodEnd = toInstant(item.getCurrentPeriodEnd());

        Long planId = resolvePlanId(item.getPrice());

        subscriptionService.updateSubscription(subscription.getId(), subscriptionStatus, periodStart, periodEnd,subscription.getCancelAtPeriodEnd(), planId);

    }

    private void handleCustomerSubscriptionDeleted(Subscription subscription){
        if(subscription==null)
        {
            log.error("Subscription object is null");
        }
        subscriptionService.cancelSubscription(subscription.getId());
    }

    private void handleInvoicePaid(String invoiceId){

        if(invoiceId==null)
        {
            log.error("InvoiceId is null");
            return;
        }


        try {
            Invoice invoice = Invoice.retrieve(invoiceId);

            String subId = extractSubscriptionId(invoice);
            if(subId == null) return;
            Subscription subscription = Subscription.retrieve(subId); //sdk calling the Stripe server
            var item = subscription.getItems().getData().get(0);

            Instant periodStart = toInstant(item.getCurrentPeriodStart());
            Instant periodEnd = toInstant(item.getCurrentPeriodEnd());

            subscriptionService.renewSubscriptionPeriod(
                    subId,
                    periodStart,
                    periodEnd
            );

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleInvoicePaymentFailed(String invoiceId){
        if(invoiceId==null)
        {
            log.error("InvoiceId is null");
            return;
        }
        try {
            Invoice invoice = Invoice.retrieve(invoiceId);
            if(invoice==null)
            {
                log.error("Invoice object is null");
                return;
            }
            String subId = extractSubscriptionId(invoice);
            if(subId == null) return;

            subscriptionService.markSubscriptionPastDue(subId);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }


    }



    // Utility Methods

    private SubscriptionStatus mapStripeStatusToEnum(String status) {
        return switch (status) {
            case "active" -> SubscriptionStatus.ACTIVE;
            case "trialing" -> SubscriptionStatus.TRIALING;
            case "past_due", "unpaid", "paused", "incomplete_expired" -> SubscriptionStatus.PAST_DUE;
            case "canceled" -> SubscriptionStatus.CANCELED;
            case "incomplete" -> SubscriptionStatus.INCOMPLETE;
            default -> {
                log.warn("Unmapped Stripe status: {}", status);
                yield null; // return empty
            }
        };
    }

    private Instant toInstant(Long epoch) {
        return epoch != null ? Instant.ofEpochSecond(epoch) : null;
    }

    private Long resolvePlanId(Price price) {
        if (price == null || price.getId() == null) return null;
        return planRepository.findByStripePriceId(price.getId())
                .map(Plan::getId)
                .orElse(null);
    }

    private String extractSubscriptionId(Invoice invoice) {
        var parent = invoice.getParent();
        if (parent == null) return null;

        var subDetails = parent.getSubscriptionDetails();
        if (subDetails == null) return null;

        return subDetails.getSubscription();
    }

    private @NonNull User getUser(Long currentUserId) {
        User customer = userRepository.findById(currentUserId).orElseThrow(()->{
            return new ResourceNotFoundException("user", currentUserId.toString());
        });
        return customer;
    }



}
