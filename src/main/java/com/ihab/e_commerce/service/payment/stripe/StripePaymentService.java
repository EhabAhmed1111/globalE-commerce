package com.ihab.e_commerce.service.payment.stripe;


import com.ihab.e_commerce.data.repo.PaymentRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.exception.PaymentFailedException;
import com.ihab.e_commerce.rest.request.stripe_payment.StripePaymentRequest;
import com.ihab.e_commerce.service.order.OrderService;
import com.ihab.e_commerce.service.payment.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripePaymentService implements PaymentService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Value("${stripe.webhook.key}")
    private String webhookKey;
//
//    private final PaymentRepo paymentRepo;
//    private final OrderService orderService;


    // this will run after all
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(StripePaymentRequest request) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(request.amount())
                    .setCurrency(request.currency())
                    .setDescription(request.description())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();
            return PaymentIntent.create(params);
        } catch (Exception e) {
            throw new PaymentFailedException("payment failed for " + e.getMessage());
        }

    }

    public Event handleWebhook(String payload, String sigHeader) {
        Event event = null;
        if (sigHeader != null && webhookKey != null){
            try {
                event = Webhook.constructEvent(payload, sigHeader, webhookKey);
            } catch (SignatureVerificationException e) {
                log.error("Webhook error while validating signature.{}", e.getMessage());
            }

        }
        if (event != null){
            handleVerifiedEvent(event);
            return event;
        }else {
            throw new GlobalNotFoundException("there is no event yet");
        }
    }

    private void handleVerifiedEvent(Event event) {
        switch (event.getType()) {
            case "payment_intent.succeeded":
                // Handle payment success
                handlePaymentSuccess(event);
                break;
            case "payment_intent.payment_failed":
                // Handle payment failure
                handlePaymentFailure(event);
                break;
        }
    }

    private void handlePaymentSuccess(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);

        log.info("Payment succeeded: {}", paymentIntent.getId());
    }

    private void handlePaymentFailure(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        // Process failed payment
        log.error("Payment failed: {}", paymentIntent.getId());
    }
}
