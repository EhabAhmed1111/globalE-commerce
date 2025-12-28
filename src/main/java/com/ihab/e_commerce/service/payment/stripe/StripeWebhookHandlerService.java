package com.ihab.e_commerce.service.payment.stripe;


import com.ihab.e_commerce.data.enums.OrderStatus;
import com.ihab.e_commerce.data.enums.PaymentStatus;
import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.data.model.Payment;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.CartRepo;
import com.ihab.e_commerce.data.repo.OrderRepo;
import com.ihab.e_commerce.data.repo.PaymentRepo;
import com.ihab.e_commerce.data.repo.ProductRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.service.cart.CartService;
import com.ihab.e_commerce.service.order.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookHandlerService {

    @Value("${stripe.webhook.key}")
    private String webhookKey;

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;
    private final CartRepo cartRepo;

    public void handleWebhook(String payload, String sigHeader) {

        if (sigHeader == null ) {
            log.error("Missing signature header in webhook");
            throw new IllegalArgumentException("Missing signature header");
        }
        if (webhookKey == null ) {
            log.error("Webhook key not configured");
            throw new IllegalStateException("Webhook key not configured");
        }

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookKey);
        } catch (SignatureVerificationException e) {
            log.error("Webhook error while validating signature.{}", e.getMessage());
            throw new SecurityException("Invalid webhook signature", e);
        }

        if (event != null) {
            handleVerifiedEvent(event);
        } else {
            log.error("Webhook event construction returned null");
            throw new IllegalStateException("Failed to construct webhook event");
        }
    }

    private void handleVerifiedEvent(Event event) {
        switch (event.getType()) {
            case "payment_intent.succeeded":
                // Handle payment success
                handlePaymentSuccess(event);
                break;
            case "payment_intent.payment_failed":
                // Handle payment failure
                handlePaymentFailure(event);
                break;
            default:
                log.debug("Unhandled webhook event type: {}", event.getType());
                // Depending on requirements, you might want to throw an exception
                // or just log and ignore unknown event types
                break;
        }
    }

    private void handlePaymentSuccess(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        if (paymentIntent == null) {
            throw new GlobalNotFoundException("Payment can not be null");
        }
        Payment payment = paymentRepo.findById(paymentIntent.getId()).orElseThrow(
                () -> new GlobalNotFoundException("There is no Payment here")
        );

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        Order order = payment.getOrder();
        order.setOrderStatus(OrderStatus.PAYMENT_DONE);

        reduceTheAmountOfProductAfterTheOrderDone(order);

        clearCartNonActiveCart();

        changeActiveConditionForCurrentCart();

        paymentRepo.save(payment);

        orderRepo.save(order);
        log.info("Payment succeeded: {}", paymentIntent.getId());
    }

    private void reduceTheAmountOfProductAfterTheOrderDone(Order order) {
        order.getOrderItems().forEach(
                (item) -> {
                    Product product = item.getProduct();
                    log.info("Change the amount of product: {}", product.getId());
                    product.setAmount(product.getAmount() - item.getQuantity());
                    productRepo.save(product);
                }
        );
    }

    private void clearCartNonActiveCart() {
        if (cartService.getCartByItsActivationForCurrentUser(false) != null) {
            log.info("Remove the non active cart: {}", cartService.getCartByItsActivationForCurrentUser(false).getId());
            cartService.clearCart(cartService.getCartByItsActivationForCurrentUser(false).getId());
        }
    }

    private void changeActiveConditionForCurrentCart() {
        Cart cart = cartService.getCartByItsActivationForCurrentUser(true);
        log.info("Change activation for cart: {}", cart.getId());
        cart.setIsActive(false);
        cartRepo.save(cart);
    }


    private void handlePaymentFailure(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        if (paymentIntent == null) {
            throw new GlobalNotFoundException("Payment can not be null");
        }
        Payment payment = paymentRepo.findById(paymentIntent.getId()).orElseThrow(
                () -> new GlobalNotFoundException("There is no Payment here")
        );

        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        Order order = payment.getOrder();
        order.setOrderStatus(OrderStatus.CANCELED);

        paymentRepo.save(payment);

        orderRepo.save(order);
        // Process failed payment
        log.error("Payment failed: {}", paymentIntent.getId());
    }

}
