package com.ihab.e_commerce.rest.response;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalExceptionResponse {
    private String message;
    private int status;
    private Long date;

}
