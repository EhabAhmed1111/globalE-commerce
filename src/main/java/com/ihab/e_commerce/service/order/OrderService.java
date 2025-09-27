package com.ihab.e_commerce.service.order;


import com.ihab.e_commerce.data.repo.OrderItemRepo;
import com.ihab.e_commerce.data.repo.OrderRepo;
import com.ihab.e_commerce.service.cart.CartService;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final UserService userService;
    private final CartService cartService;
    private final OrderItemRepo orderItemRepo;

    // I need to get user and get his cart and make it order
}
