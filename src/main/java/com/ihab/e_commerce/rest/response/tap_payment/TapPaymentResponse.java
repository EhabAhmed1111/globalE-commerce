package com.ihab.e_commerce.rest.response.tap_payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ihab.e_commerce.data.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TapPaymentResponse {
    private String id;
    private Long orderId;
    private PaymentStatus status;
    private String message;
    private BigDecimal amount;
    private String currency;

    @JsonProperty("redirect_url")
    private String redirectUrl;
//    private TapChargeResponse tapResponse;

//    @JsonProperty("client_secret")
//    private String clientSecret;
}
