package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.data.dto.OrderItemDto;
import com.ihab.e_commerce.data.enums.OrderStatus;
import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.data.model.OrderItem;
import com.ihab.e_commerce.rest.response.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderItemMapper {

    private final ProductMapper productMapper;

    public OrderItem fromOrderItemDtoToOrderItem(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .quantity(orderItemDto.getQuantity())
                .id(orderItemDto.getId())
                .unitePrice(orderItemDto.getUnitePrice())
                .createdAt(orderItemDto.getCreatedAt())
                .totalPrice(orderItemDto.getTotalPrice())
                .product(productMapper.fromResponseToProduct(orderItemDto.getProductResponse()))
                .build();
    }

    public OrderItemDto fromOrderItemToDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .quantity(orderItem.getQuantity())
                .id(orderItem.getId())
                .unitePrice(orderItem.getUnitePrice())
                .createdAt(orderItem.getCreatedAt())
                .totalPrice(orderItem.getTotalPrice())
                .productResponse(productMapper.fromProductToProductResponse(orderItem.getProduct()))
                .build();
    }

    public Set<OrderItemDto> fromSetOfOrderItemToSetOfOrderItemsDto(Set<OrderItem> orderItems) {
        return orderItems == null ? null
                : orderItems.stream().map(this::fromOrderItemToDto).collect(Collectors.toSet());
    }

    public OrderItemResponse fromOrderItemToOrderItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .buyerName(orderItem.getOrder().getBuyer().getFirstName() + " " + orderItem.getOrder().getBuyer().getLastName())
                .orderStatus(orderItem.getOrder().getOrderStatus())
                .totalPrice(orderItem.getTotalPrice())
                .createdAt(orderItem.getCreatedAt())
                .quantity(orderItem.getQuantity())
                .unitePrice(orderItem.getUnitePrice())
                .build();
    }

    public List<OrderItemResponse> fromListOfOrderItemToListOfOrderItemResponse(List<OrderItem> orderItems) {
        return orderItems == null ? Collections.emptyList() :
                orderItems
                        .stream()
                        .map(this::fromOrderItemToOrderItemResponse)
                        .collect(Collectors.toList());
    }

}
