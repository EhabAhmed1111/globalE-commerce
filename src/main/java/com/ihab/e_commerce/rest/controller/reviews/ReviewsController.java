package com.ihab.e_commerce.rest.controller.reviews;


import com.ihab.e_commerce.data.dto.ReviewsDto;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.rest.response.ReviewsResponse;
import com.ihab.e_commerce.service.reviews.ReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reviews")
public class ReviewsController {

    final private ReviewsService reviewsService;

    // todo create this

    @GetMapping("/product/{productId}")
    public ResponseEntity<GlobalSuccessResponse> getAllReviews(
            @PathVariable Long productId
    ) {
        List<ReviewsResponse> reviewsResponses = reviewsService.getAllReviewForProduct(productId);
        return ResponseEntity.ok(
                GlobalSuccessResponse.builder()
                        .data(reviewsResponses)
                        .message("Reviews fetched successfully")
                        .build()
        );
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<GlobalSuccessResponse> deleteReview(
            @PathVariable Long productId,
            @RequestParam Long reviewId
    ) {
        reviewsService.deleteRepo(productId, reviewId);
        return ResponseEntity.ok(
                GlobalSuccessResponse.builder()
                        .data(null)
                        .message("Reviews deleted successfully")
                        .build()
        );
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<GlobalSuccessResponse> addReview(
            @RequestBody ReviewsDto reviewsDto,
            @PathVariable Long productId
    ) {
        ReviewsResponse reviewsResponse = reviewsService.addReview(reviewsDto, productId);
        return ResponseEntity.ok(
                GlobalSuccessResponse.builder()
                        .data(reviewsResponse)
                        .message("Reviews deleted successfully")
                        .build()
        );
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<GlobalSuccessResponse> updateReview(
            @RequestBody ReviewsDto request,
            @PathVariable Long productId,
            @RequestParam Long reviewId
    ) {
        ReviewsResponse reviewsResponse = reviewsService.updateReview(productId, reviewId, request);
        return ResponseEntity.ok(
                GlobalSuccessResponse.builder()
                        .data(reviewsResponse)
                        .message("Reviews deleted successfully")
                        .build()
        );
    }
}
