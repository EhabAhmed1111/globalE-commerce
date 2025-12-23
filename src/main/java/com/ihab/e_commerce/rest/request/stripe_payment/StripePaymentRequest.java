package com.ihab.e_commerce.rest.request.stripe_payment;

import java.math.BigDecimal;

public record StripePaymentRequest(
    Long amount,
    String currency,
    String description
) {
}
