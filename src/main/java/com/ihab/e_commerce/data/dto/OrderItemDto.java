package com.ihab.e_commerce.data.dto;

import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.rest.response.ProductResponse;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long id;
    private BigDecimal totalPrice;
    private BigDecimal unitePrice;
    private LocalDateTime createdAt;
    private Integer quantity;
    private ProductResponse productResponse;

}
