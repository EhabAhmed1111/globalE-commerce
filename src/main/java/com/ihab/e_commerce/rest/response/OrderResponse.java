package com.ihab.e_commerce.rest.response;

import com.ihab.e_commerce.data.dto.OrderItemDto;
import com.ihab.e_commerce.data.dto.UserDto;
import com.ihab.e_commerce.data.enums.OrderStatus;
import com.ihab.e_commerce.data.model.OrderItem;
import com.ihab.e_commerce.data.model.Payment;
import com.ihab.e_commerce.data.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse {

    private Long id;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private OrderStatus orderStatus;
    private UserDto user;
    private Set<OrderItemDto> orderItems;
    private String clientSecret;

//    private Set<Payment> payments;

}
