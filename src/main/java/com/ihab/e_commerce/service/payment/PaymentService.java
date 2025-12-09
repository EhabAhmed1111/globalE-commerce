package com.ihab.e_commerce.service.payment;


import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.rest.request.payment.*;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;


// this should be intent design pattern
// todo change name to intent after you learn it
@Service
@RequiredArgsConstructor
public class PaymentService {



    private final WebClient webClient;
    private final UserService userService;

    public Mono<String> createPaymentIntent(BigDecimal amount) {
User user = userService.loadCurrentUser();
        PaymentIntentRequest request = new PaymentIntentRequest(
                // todo make the currency depend on user
                new Amount("USD", amount),
                new Customer(user.getEmail()),
                // todo make this depend on user preferred payment method
                new Source("src_card"),
                new Redirect("https://yourdomain.com/api/payments/redirect"),
                new Webhook("https://yourdomain.com/api/payments/webhook")
        );

     return webClient.post()
             .uri("/payment_intents")
             .bodyValue(request)
             .retrieve()
//             .onStatus(HttpStatusCode::isError, response ->
//                     response.bodyToMono(String.class)
//                             .map(body -> new RuntimeException("Tap Payment error: " + body))
//             )
             .bodyToMono(String.class);
    }
}
