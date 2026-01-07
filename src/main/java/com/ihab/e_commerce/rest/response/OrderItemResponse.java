package com.ihab.e_commerce.rest.response;


import com.ihab.e_commerce.data.enums.OrderStatus;
import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.data.model.Product;
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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemResponse {

    private Long id;
    private BigDecimal totalPrice;
    private BigDecimal unitePrice;
    private LocalDateTime createdAt;
    private Integer quantity;
    private OrderStatus orderStatus;
    private String buyerName;
}
