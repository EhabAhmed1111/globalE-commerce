package com.ihab.e_commerce.rest.response.stripe_payment;

import com.ihab.e_commerce.data.enums.PaymentStatus;

import java.math.BigDecimal;

public record StripePaymentResponse(
        String id,
        String clientSecret,
        Long amount,
        String currency,
        String status
) {
}
