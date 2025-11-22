package com.ihab.e_commerce.rest.response;

import com.ihab.e_commerce.data.dto.MediaDto;
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
public class ProductResponse {
// This is what user will see
/* todo here there should be an id
*   and covering image url here*/
    private String productName;
    private BigDecimal price;
    private String brand;
    private Integer amount;
    private String description;
    private String categoryName;
    private List<MediaDto> medias;
}
