package com.ihab.e_commerce.service.cart;

import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.data.repo.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;



    // TODO(CRUD)

    public Cart initializeCart(Long userId){
        // TODO(I NEED TO CREATE USER SERVICE)
        Cart cart = Cart.builder()
                .build();

        return cart;
    }

    public Cart getCartByUserId(Long userId){
        return cartRepo.findByUserId(userId);
    }
}
