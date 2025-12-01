package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.data.dto.ReviewsDto;
import com.ihab.e_commerce.data.model.Reviews;
import com.ihab.e_commerce.rest.response.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewsMapper {

    final private UserMapper userMapper;

    public Reviews fromDtoToReview(ReviewsDto reviewsDto) {
        return Reviews.builder()
                .content(reviewsDto.getContent())
                .createdAt(reviewsDto.getCreateAt())
                .id(reviewsDto.getId())
                .rating(reviewsDto.getRating())
                .build();
    }

    public ReviewsDto fromReviewToDto(Reviews reviews) {
        return ReviewsDto.builder()
                .content(reviews.getContent())
                .createAt(reviews.getCreatedAt())
                .rating(reviews.getRating())
                .id(reviews.getId())
                .build();
    }

    public Reviews fromReviewResponseToReview(ReviewsResponse reviewsResponse) {
        return Reviews.builder()
                .content(reviewsResponse.getContent())
                .createdAt(reviewsResponse.getCreateAt())
                .id(reviewsResponse.getId())
                .user(userMapper.fromDtoToUser(reviewsResponse.getUserDto()))
                .rating(reviewsResponse.getRating())
                .build();
    }

    public ReviewsResponse fromReviewToReviewResponse(Reviews reviews) {
        return ReviewsResponse.builder()
                .userDto(userMapper.fromUserToDto(reviews.getUser()))
                .content(reviews.getContent())
                .createAt(reviews.getCreatedAt())
                .rating(reviews.getRating())
                .id(reviews.getId())
                .build();
    }


}
