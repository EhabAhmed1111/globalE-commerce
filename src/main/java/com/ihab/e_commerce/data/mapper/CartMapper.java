package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.rest.response.CartResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class CartMapper {
    private final CartItemMapper cartItemMapper;
private final UserMapper userMapper;

    public CartResponse fromCartToResponse(Cart cart) {

        return CartResponse.builder()
                .cartItemsDto(cartItemMapper.fromSetOfCartItemToDtos(cart.getCartItems()))
                .id(cart.getId())
                .createdAt(cart.getCreatedAt())
                .isActive(cart.getIsActive())
                .userDto(userMapper.fromUserToDto(cart.getUser()))
                .updatedAt(cart.getUpdatedAt())
                .totalPrice(cart.getTotalPrice())
                .build();
    }
}
