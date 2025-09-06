package com.ihab.e_commerce.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productName;
    private BigDecimal price;
    private String brand;
    private Integer amount;
    private String description;
    private String categoryName;

}
