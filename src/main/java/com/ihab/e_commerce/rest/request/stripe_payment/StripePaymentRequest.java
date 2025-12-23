package com.ihab.e_commerce.rest.request.stripe_payment;

import java.math.BigDecimal;

public record StripePaymentRequest(
    BigDecimal amount,
    String currency,
    String description
) {
}
