package com.ihab.e_commerce.service.reviews;

import com.ihab.e_commerce.data.dto.ReviewsDto;
import com.ihab.e_commerce.data.mapper.ReviewsMapper;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.model.Reviews;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.data.repo.ProductRepo;
import com.ihab.e_commerce.data.repo.ReviewsRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.exception.GlobalUnauthorizedActionException;
import com.ihab.e_commerce.rest.request.ReviewRequest;
import com.ihab.e_commerce.rest.response.ReviewsResponse;
import com.ihab.e_commerce.service.product.ProductService;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ihab.e_commerce.data.enums.Role.ADMIN;

@RequiredArgsConstructor
@Service
public class ReviewsService {

    private final ReviewsRepo reviewsRepo;
    private final ProductService productService;
    private final UserService userService;
    private final ReviewsMapper reviewsMapper;
    private final ProductRepo productRepo;

    /* we should use it inside the product service */
//    public Integer getAvgRateForProduct(Long productId) {
//        Product product = productService.getProductForOthersClasses(productId);
//        return product.getReviews() == null ? null
//                : product.getReviews()
//                .stream()
//                .map(Reviews::getRating)
//                .reduce(0, Integer::sum);
//    }


    /* todo make reviewDto*/
    public ReviewsResponse addReview(ReviewsDto reviewsDto, Long productId) {
        Product product = productService.getProductForOthersClasses(productId);
       Reviews reviews =  reviewsRepo.save(buildReview(reviewsDto, product));
       product.calcAvgRating();
        productRepo.save(product);
        return reviewsMapper.fromReviewToReviewResponse(reviews);
    }

    private Reviews buildReview(ReviewsDto reviewsDto, Product product) {

        Reviews reviews = reviewsMapper.fromDtoToReview(reviewsDto);
        User user = userService.loadCurrentUser();
        reviews.setProduct(product);
        reviews.setUser(user);
        return reviews;
    }

    public void deleteRepo(Long reviewId, Long productId) {
        Product product = productService.getProductForOthersClasses(productId);
        Reviews reviews = findingReview(product, reviewId);
        User currentUser = userService.loadCurrentUser();
        if (currentUser != reviews.getUser() || currentUser.getRole() != ADMIN) {
            throw new GlobalUnauthorizedActionException("this user can't delete this comment");
        }
        reviewsRepo.delete(reviews);
        product.calcAvgRating();
        productRepo.save(product);
    }

    public List<ReviewsResponse> getAllReviewForProduct(Long productId) {
        Product product = productService.getProductForOthersClasses(productId);
        return product.getReviews().stream().map(reviewsMapper::fromReviewToReviewResponse).collect(Collectors.toList());
    }

    public ReviewsResponse updateReview(Long productId, Long reviewsId, ReviewRequest request) {
        User currentUser = userService.loadCurrentUser();
        Product product = productService.getProductForOthersClasses(productId);
        Reviews reviews = updatingReview(product, reviewsId, request.getComment(), request.getRating());
        if (currentUser != reviews.getUser() || currentUser.getRole() != ADMIN) {
            throw new GlobalUnauthorizedActionException("this user can't delete this comment");
        }
        Reviews savedReview =  reviewsRepo.save(reviews);
        product.calcAvgRating();
        productRepo.save(product);
        return reviewsMapper.fromReviewToReviewResponse(savedReview);
    }

    private Reviews updatingReview(Product product, Long reviewsId, String content, Integer rating) {
        Reviews reviews = findingReview(product, reviewsId);
        reviews.setContent(content);
        reviews.setRating(rating);
        return reviews;
    }

    private Reviews findingReview(Product product, Long reviewsId) {
        List<Reviews> reviews = product.getReviews();
        return reviews.stream().filter(
                reviewsWithinProduct -> !Objects.equals(reviewsWithinProduct.getId(), reviewsId)
        ).findFirst().orElseThrow(
                () -> new GlobalNotFoundException("there is no reviews for this product to update")
        );
    }


}
