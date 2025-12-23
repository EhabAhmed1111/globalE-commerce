package com.ihab.e_commerce.data.mapper;


import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.rest.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;


    public OrderResponse fromOrderToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .user(userMapper.fromUserToDto(order.getUser()))
                .orderItems(orderItemMapper.fromSetOfOrderItemToSetOfOrderItemsDto(order.getOrderItems()))
                .build();
    }

}
