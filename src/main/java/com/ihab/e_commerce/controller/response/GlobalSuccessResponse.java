package com.ihab.e_commerce.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobalSuccessResponse {
    private String message;
    private Object data;
}
