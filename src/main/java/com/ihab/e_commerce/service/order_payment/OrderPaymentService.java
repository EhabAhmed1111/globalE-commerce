//package com.ihab.e_commerce.service.order_payment;
//
//import com.ihab.e_commerce.data.repo.OrderRepo;
//import com.ihab.e_commerce.service.order.OrderService;
//import com.ihab.e_commerce.service.payment.TapPaymentService;
//import com.ihab.e_commerce.service.user.main.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Service
//@RequiredArgsConstructor
//public class OrderPaymentService {
//
////    private final WebClient webClient;
////    private final UserService userService;
////    private final OrderRepo orderRepo;
//    private final OrderService orderService;
//    private final TapPaymentService tapPaymentService;
//
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
//
//}
