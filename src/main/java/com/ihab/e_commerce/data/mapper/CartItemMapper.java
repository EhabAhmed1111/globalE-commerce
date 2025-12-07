package com.ihab.e_commerce.data.mapper;


import com.ihab.e_commerce.data.dto.CartItemDto;
import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.data.model.CartItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
@Component
public class CartItemMapper {

private final ProductMapper productMapper;

    public CartItem fromDtoToCartItem(CartItemDto cartItemDto) {
        return CartItem.builder()
                .id(cartItemDto.getId())
                .unitePrice(cartItemDto.getUnitePrice())
                .product(productMapper.fromDtoToProduct(cartItemDto.getProductDto()))
                .quantity(cartItemDto.getQuantity())
                .createdAt(cartItemDto.getCreatedAt())
                .updatedAt(cartItemDto.getUpdatedAt())
                .totalPrice(cartItemDto.getUnitePrice())
                .build();
    }

    public CartItemDto fromCartItemToDto(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .unitePrice(cartItem.getUnitePrice())
                .productDto(productMapper.fromProductToDto(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .createdAt(cartItem.getCreatedAt())
                .updatedAt(cartItem.getUpdatedAt())
                .totalPrice(cartItem.getUnitePrice())
                .build();
    }

    public Set<CartItemDto> fromSetOfCartItemToDtos(Set<CartItem> cartItems) {
        return cartItems.stream().map(
                cartItem -> {
                    return CartItemDto.builder()
                            .id(cartItem.getId())
                            .unitePrice(cartItem.getUnitePrice())
                            .productDto(productMapper.fromProductToDto(cartItem.getProduct()))
                            .quantity(cartItem.getQuantity())
                            .createdAt(cartItem.getCreatedAt())
                            .updatedAt(cartItem.getUpdatedAt())
                            .totalPrice(cartItem.getUnitePrice())
                            .build();
                }
        ).collect(Collectors.toSet());
    }
}
