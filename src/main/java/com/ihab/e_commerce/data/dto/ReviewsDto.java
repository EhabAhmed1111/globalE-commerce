package com.ihab.e_commerce.data.dto;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewsDto {
    private Integer rating;
    private String content;
}
