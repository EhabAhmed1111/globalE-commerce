package com.ihab.e_commerce.rest.controller.payment;

import com.ihab.e_commerce.rest.request.stripe_payment.StripePaymentRequest;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.rest.response.stripe_payment.StripePaymentResponse;
import com.ihab.e_commerce.service.payment.stripe.StripePaymentService;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
public class StripePaymentController {

    private final StripePaymentService paymentService;

    @PostMapping("/intent")
    public ResponseEntity<GlobalSuccessResponse> createIntent(@RequestBody StripePaymentRequest request) {
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(request);
        StripePaymentResponse stripePaymentResponse = new StripePaymentResponse(
                paymentIntent.getId(),
                paymentIntent.getClientSecret(),
                paymentIntent.getAmount(),
                paymentIntent.getCurrency(),
                paymentIntent.getStatus()
        );
        return ResponseEntity.ok(new GlobalSuccessResponse("Payment created successfully", stripePaymentResponse));
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader ) {
        log.info("Received webhook with signature: {}", sigHeader);

        paymentService.handleWebhook(payload, sigHeader);

        return ResponseEntity.ok().build();
    }
}

