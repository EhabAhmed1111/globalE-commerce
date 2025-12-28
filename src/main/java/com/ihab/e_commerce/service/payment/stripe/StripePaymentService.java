package com.ihab.e_commerce.service.payment.stripe;


import com.ihab.e_commerce.data.enums.PaymentStatus;
import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.data.model.Payment;
import com.ihab.e_commerce.data.repo.PaymentRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.exception.PaymentFailedException;
import com.ihab.e_commerce.rest.request.stripe_payment.StripePaymentRequest;
import com.ihab.e_commerce.rest.response.stripe_payment.StripePaymentResponse;
import com.ihab.e_commerce.service.order.OrderService;
import com.ihab.e_commerce.service.payment.PaymentService;
import com.ihab.e_commerce.service.user.main.UserService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripePaymentService implements PaymentService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    private final PaymentRepo paymentRepo;
    private final UserService userService;


    // this will run after all
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }


//    private final PaymentRepo paymentRepo;
//    private final OrderService orderService;


    public PaymentIntent createPaymentIntent(Order order, StripePaymentRequest request) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(request.amount().longValue())
                    .setCurrency(request.currency())
                    .setDescription(request.description())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // here we save the payment inside the db
            Payment payment = Payment.builder()
                    .id(paymentIntent.getId())
                    .amount(order.getTotalPrice())
                    .currency("USD")
                    .paymentStatus(PaymentStatus.PENDING)
                    .paymentGateway("STRIPE")
                    .paymentMethod(paymentIntent.getPaymentMethod())
                    .user(userService.loadCurrentUser())
                    .order(order)
                    .build();

            paymentRepo.save(payment);

            return paymentIntent;
        } catch (Exception e) {
            throw new PaymentFailedException("payment failed for " + e.getMessage());
        }

    }


    public StripePaymentResponse getPaymentById(String id) {
        Payment payment = paymentRepo.findById(id).orElseThrow(
                ()-> new GlobalNotFoundException("there is no payment")
        );
        return new StripePaymentResponse(
                payment.getId(),
                null,
                payment.getAmount().longValue(),
                payment.getCurrency(),
                payment.getPaymentStatus().name()
        );
    }
}
