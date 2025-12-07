package com.ihab.e_commerce.data.dto;


import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.data.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private Long id;
    private BigDecimal unitePrice;
    private BigDecimal totalPrice;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Integer quantity;
    private ProductDto productDto;


}
