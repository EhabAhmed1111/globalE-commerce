package com.ihab.e_commerce.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    // We use this to transfer the data from user to server
    private String productName;
    private BigDecimal price;
    private String brand;
    private Integer amount;
    private String description;
    private String categoryName;
    private Double avgRate;
    private List<MediaDto> mediaDtoList;
}
