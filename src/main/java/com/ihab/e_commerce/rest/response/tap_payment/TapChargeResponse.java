package com.ihab.e_commerce.rest.response.tap_payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TapChargeResponse(
        // Core response fields
        @JsonProperty("id") String chargeId,

        // Payment status
        @JsonProperty("status") String status, // INITIATED, CAPTURED, FAILED, CANCELLED
        @JsonProperty("paid") Boolean paid,
        @JsonProperty("captured") Boolean captured,
        @JsonProperty("refunded") Boolean refunded,

        // Amount details
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("currency") String currency,

        // Customer info
        @JsonProperty("customer") Customer customer,


        // Payment method details
        @JsonProperty("payment_method") String paymentMethod, // "CARD", "APPLE_PAY", etc.

        // Redirect/3DS handling
        @JsonProperty("redirect") Redirect redirect,

        // Timing
        @JsonProperty("created") Long createdTimestamp,
        @JsonProperty("expiry") ExpiryDetails expiry,

        // Receipt/URLs
        @JsonProperty("receipt") Receipt receipt,
        @JsonProperty("redirect_url") String redirectUrl,
        @JsonProperty("post_url") String postUrl,

        // Metadata
        @JsonProperty("metadata") Map<String, String> metadata,
        @JsonProperty("reference") Reference reference,
    @JsonProperty("failure_message") String failureMessage
) {

    // Nested objects
    public record Customer(
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email
    ) {}


    public record Redirect(
            @JsonProperty("status") String status,
            @JsonProperty("url") String url
    ) {}


    public record ExpiryDetails(
            @JsonProperty("period") Integer period,
            @JsonProperty("type") String type // "MINUTE", "HOUR"
    ) {}

    public record Receipt(
            @JsonProperty("id") String receiptId,
            @JsonProperty("email") Boolean emailSent,
            @JsonProperty("sms") Boolean smsSent
    ) {}

    public record Reference(
            @JsonProperty("transaction") String transaction,
            @JsonProperty("order") String order,
            @JsonProperty("gateway") String gateway
    ) {}

}