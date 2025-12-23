package com.ihab.e_commerce.rest.request.tap_payment;

import java.math.BigDecimal;

public record WebhookRequest(
        // this id from tap
        String id,
                             String event,
                             TapChargeData data,
                             String created,
                             String api_version) {
    public record TapChargeData(
            Long id,
            String status,
            BigDecimal amount,
            String currency,
            String customer_id,
//            Source source,
            Redirect redirect
    ) {
//        public record Source(
//                String channel,
//                String payment_method,
//                Card card
//        ) {
//            public record Card(
//                    String last_four,
//                    String brand,
//                    String funding
//            ) {
//            }
//        }

        public record Redirect(String url) {
        }
    }
}
