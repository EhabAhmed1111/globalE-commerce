package com.ihab.e_commerce.rest.controller.reviews;


import com.ihab.e_commerce.service.reviews.ReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reviews")
public class ReviewsController {

    final private ReviewsService reviewsService;

    // todo create this
}
