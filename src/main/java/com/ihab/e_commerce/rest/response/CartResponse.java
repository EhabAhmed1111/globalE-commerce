package com.ihab.e_commerce.rest.response;

import com.ihab.e_commerce.data.dto.CartItemDto;
import com.ihab.e_commerce.data.dto.UserDto;
import com.ihab.e_commerce.data.model.CartItem;
import com.ihab.e_commerce.data.model.User;
import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    private Long id;
    private BigDecimal totalPrice;
    private Boolean isActive;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Set<CartItemDto> cartItemsDto;
    private UserDto userDto;
}
