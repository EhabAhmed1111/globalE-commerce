package com.ihab.e_commerce.rest.response;

import com.ihab.e_commerce.data.dto.MediaDto;
import com.ihab.e_commerce.data.model.User;
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

    private Long id;
    private String productName;
    private BigDecimal price;
    private String brand;
    private Integer amount;
    private String description;
    private String categoryName;
    private List<MediaDto> medias;
    /* here we get the user */
    private User vendor;
}
