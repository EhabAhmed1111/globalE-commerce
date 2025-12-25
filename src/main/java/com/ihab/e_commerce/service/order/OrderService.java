package com.ihab.e_commerce.service.order;


import com.ihab.e_commerce.data.enums.OrderStatus;
import com.ihab.e_commerce.data.mapper.OrderMapper;
import com.ihab.e_commerce.data.model.*;
import com.ihab.e_commerce.data.repo.CartRepo;
import com.ihab.e_commerce.data.repo.OrderItemRepo;
import com.ihab.e_commerce.data.repo.OrderRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.rest.request.stripe_payment.StripePaymentRequest;
import com.ihab.e_commerce.rest.response.OrderResponse;
import com.ihab.e_commerce.rest.response.stripe_payment.StripePaymentResponse;
import com.ihab.e_commerce.service.cart.CartService;
import com.ihab.e_commerce.service.payment.stripe.StripePaymentService;
import com.ihab.e_commerce.service.user.main.UserService;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    // todo(when order is made of a cart -> change isActive to false)
    /*
     * todo if order made for same user for other cart
     *  user has cart1 is not active and another cart2 he made order for cart2
     *  then cart1 must be deleted and cart2 isActive will change to false*/
    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final OrderItemRepo orderItemRepo;
//    private final UserService userService;
//    private final CartRepo cartRepo;
    private final OrderMapper orderMapper;
    private final StripePaymentService stripePaymentService;

    // Creation OP
    /* todo I need to make condition that if there another cart that has active false
    *   then this cart will deleted and current cart will become false active*/
    public OrderResponse makeOrder() {
        // this is the current active cart
        Cart cart = cartService.getCartByItsActivationForCurrentUser(true);
        // todo here i need to get the previous active cart in order to delete it
        Order order = makeOrder(cart);
        StripePaymentResponse stripePaymentResponse =  createPaymentForOrder(order, "USD");
//        cart.setIsActive(false);
//        cartRepo.save(cart);
        OrderResponse orderResponse = orderMapper.fromOrderToOrderResponse(order);
        orderResponse.setClientSecret(stripePaymentResponse.clientSecret());
        return orderResponse;
    }

    // ReOrder OP
    // todo this maybe same functionality as find cart
    public Order reOrderLastOrder() {
//        User user = userService.loadCurrentUser();
//        List<Cart> carts = cartService.getAllCartByUserId(user.getId());
        Cart cart = cartService.getCartByItsActivationForCurrentUser(false);
//                carts.stream().filter(
//                expectedCart -> expectedCart.getIsActive() == false
//        ).findFirst().orElseThrow(
//                () -> new GlobalNotFoundException("There is no previous order for you")
//        );
        return makeOrder(cart);

    }

    private Order makeOrder(Cart cart){
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(cart.getUser());
        orderRepo.save(order);
        order.addItems(
                cart.getCartItems().stream().map(
                        item -> makeOrderItemFromCartItem(order, item)
                ).collect(Collectors.toSet())
        );
        order.setTotalPrice(cart.getTotalPrice());

        // todo here I don't know what to do here

        orderRepo.save(order);
//        order.getOrderItems().forEach(
//                item -> {
//                    Product product = item.getProduct();
//                    product.setAmount(item.getProduct().getAmount() - item.getQuantity());
//                    productRepo.save(product);
//                }
//        );
        return order;
    }

    private StripePaymentResponse createPaymentForOrder(Order order, String currency) {
        if (currency.isEmpty()){
            currency = "USD";
        }

        StripePaymentRequest stripePaymentRequest = new StripePaymentRequest(
                order.getTotalPrice(),
                currency,
                "payment created for order" + order.getId()
        );
        PaymentIntent paymentIntent = stripePaymentService.createPaymentIntent(order, stripePaymentRequest);

        return new StripePaymentResponse(
                paymentIntent.getId(),
                paymentIntent.getClientSecret(),
                paymentIntent.getAmount(),
                paymentIntent.getCurrency(),
                paymentIntent.getStatus()
        );
    }
    private OrderItem makeOrderItemFromCartItem(Order order, CartItem item) {
        OrderItem orderItem = OrderItem.builder()
                .product(item.getProduct())
                .unitePrice(item.getUnitePrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .order(order)
                .build();
        return orderItemRepo.save(orderItem);
    }

    // todo update orderStatues after inside webhook
//    public Order updateOrderState(Long orderId) {
//
//    }

    // Read OP
    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(
                () -> new GlobalNotFoundException("There is no Order with id: " + orderId)
        );
    }

    public List<Order> getAllOrderForUser(Long userId) {
        return orderRepo.findAllByUserId(userId);
    }

    // Cancel OP

    // todo make method that get the cart that has Active false

    // todo update orderItem?

}
