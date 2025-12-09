package com.ihab.e_commerce.rest.controller.payment;

import com.ihab.e_commerce.exception.GlobalConflictException;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/intent")
    public ResponseEntity<GlobalSuccessResponse> createIntent(@RequestParam BigDecimal amount) {
        Mono<String> response = paymentService.createPaymentIntent(amount);
        return ResponseEntity.ok(new GlobalSuccessResponse("Payment done successfully", response));
    }
}
