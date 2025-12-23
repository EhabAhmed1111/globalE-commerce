//package com.ihab.e_commerce.service.payment;
//
//
//import com.ihab.e_commerce.data.enums.PaymentMethod;
//import com.ihab.e_commerce.data.model.User;
//import com.ihab.e_commerce.rest.request.tap_payment.*;
//import com.ihab.e_commerce.rest.response.tap_payment.TapPaymentResponse;
//import com.ihab.e_commerce.service.user.main.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.math.BigDecimal;
//
//
//// this should be intent design pattern
//// todo change name to intent after you learn it
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class TapPaymentService {
//
//
//    private final WebClient webClient;
//    private final UserService userService;
//    private final RestTemplate restTemplate;
//
//    @Value("${tap.base_url:https://api.tap.company/v2}")
//    private String tapBaseUrl;
//
//    @Value("${payment.redirect.url:http://localhost:4200/payment/result}")
//    private String redirectUrl;
//
//    // this server-to-server
//    @Value("${payment.webhook.url:http://localhost:8080/api/v1/payment/webhook}")
//    private String webhookUrl;
//
//    @Value("${payment.default.currency:USD}")
//    private String defaultCurrency;
//
////    public TapPaymentResponse createPaymentIntent(BigDecimal amount) {
////        try {
////            User user = userService.loadCurrentUser();
////
////            // this to get the smallest currency that what tap except
////            TapPaymentIntentRequest request = getTapPaymentIntentRequest(amount, user);
////
////            String url = tapBaseUrl + "/payment_intents";
////
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_JSON);
////
////            HttpEntity<TapPaymentIntentRequest> entity = new HttpEntity<>(request, headers);
////
////            ResponseEntity<TapPaymentResponse> response = restTemplate.exchange(
////                    url,
////                    HttpMethod.POST,
////                    entity,
////                    TapPaymentResponse.class
////            );
////
////            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
////                return response.getBody();
////            } else {
////                throw new RuntimeException("Failed to create payment intent: " + response.getStatusCode());
////            }
////        } catch (Exception e) {
////            throw new RuntimeException("Payment Service Error" + e.getMessage());
////        }
////    }
////
////    private TapPaymentIntentRequest getTapPaymentIntentRequest(BigDecimal amount, User user) {
////        int amountInCents = amount.multiply(BigDecimal.valueOf(100)).intValue();
////
////        return new TapPaymentIntentRequest(
////                new Amount(defaultCurrency, BigDecimal.valueOf(amountInCents)),
////                new Customer(
////                        user.getEmail(),
////                        user.getFirstName() != null ? user.getFirstName() : "Customer",
////                        user.getLastName() != null ? user.getLastName() : ""
////                ),
////                new Source("src_card"),
////                new Redirect(redirectUrl),
////                new Post(webhookUrl)
////        );
////    }
//
//
//    public Mono<TapPaymentResponse> createPaymentIntentWithWebFlux(Long orderId, BigDecimal amount, PaymentMethod paymentMethod) {
//        User user = userService.loadCurrentUser();
//        TapPaymentIntentRequest request = new TapPaymentIntentRequest(
//                orderId,
//                // todo make the currency depend on user
//                new Amount("USD", amount),
//                new Customer(user.getEmail(), user.getFirstName(), user.getLastName()),
//                // todo make this depend on user preferred payment method
//                paymentMethod,
////                new Source("src_card"),
//                new Redirect(redirectUrl),
//                // this should change to Webhook
//                new Post(webhookUrl)
//        );
//
//        return webClient.post()
//                .uri("/v2/charges")
//                .bodyValue(request)
//                // this will send the httpReq
//                .retrieve()
////             .onStatus(HttpStatusCode::isError, response ->
////                     response.bodyToMono(String.class)
////                             .map(body -> new RuntimeException("Tap Payment error: " + body))
////             )
//                .bodyToMono(TapPaymentResponse.class)
//                .doOnSuccess(response ->
//                        log.info("Payment intent created: {}", response.getId()))
//                .doOnError(error ->
//                        log.error("Failed to create payment intent", error));
//    }
//}
