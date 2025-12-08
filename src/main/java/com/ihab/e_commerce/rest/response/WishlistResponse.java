package com.ihab.e_commerce.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private Integer count;
    private Set<ProductResponse> productResponseSet;
}
