package com.ihab.e_commerce.controller.response;

import lombok.*;

import java.time.LocalDate;


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
