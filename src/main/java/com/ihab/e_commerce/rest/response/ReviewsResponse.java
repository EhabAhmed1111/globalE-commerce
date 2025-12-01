package com.ihab.e_commerce.rest.response;

import com.ihab.e_commerce.data.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsResponse {
    private Long id;
    private Integer rating;
    private String content;
    private LocalDateTime createAt;
    private UserDto userDto;
}
