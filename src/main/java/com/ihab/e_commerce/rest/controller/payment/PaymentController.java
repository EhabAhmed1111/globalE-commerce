//package com.ihab.e_commerce.rest.controller.payment;
//
//import com.ihab.e_commerce.exception.PaymentFailedException;
//import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
//import com.ihab.e_commerce.service.payment.TapPaymentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Mono;
//
//import java.math.BigDecimal;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("${api.prefix}/payment")
//public class PaymentController {
//
//    private final TapPaymentService tapPaymentService;
//
//    @PostMapping("/intent")
//    public ResponseEntity<GlobalSuccessResponse> createIntent(@RequestParam BigDecimal amount) {
//        try {
//
//            Mono<?> paymentResponse = tapPaymentService.createPaymentIntentWithWebFlux(amount);
//            return ResponseEntity.ok(
//                    new GlobalSuccessResponse(
//                            "Payment intent created successfully",
//                         paymentResponse
//                    )
//            );
//        } catch (Exception e) {
//          throw new PaymentFailedException("Payment failed for " + e.getMessage());
//        }
//    }
//    // Simple webhook handler
//    // this what I can't understand
//    @PostMapping("/webhook")
//    public ResponseEntity<String> handleWebhook(@RequestBody String payload) {
//        System.out.println("Received webhook from Tap: " + payload);
//        // Here you would parse the JSON and update your database
//
//        // Always return 200 OK to Tap
//        return ResponseEntity.ok("Webhook received");
//    }
//
//    // Simple redirect handler
//    @GetMapping("/redirect")
//    public ResponseEntity<String> handleRedirect(
//            @RequestParam String tap_id,
//            @RequestParam(required = false) String status) {
//
//        if ("CAPTURED".equals(status)) {
//            return ResponseEntity.ok("Payment successful! Transaction ID: " + tap_id);
//        } else {
//            return ResponseEntity.badRequest().body("Payment failed or cancelled. Status: " + status);
//        }
//    }
//}
