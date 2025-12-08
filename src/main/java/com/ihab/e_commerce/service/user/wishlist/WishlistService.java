package com.ihab.e_commerce.service.user.wishlist;

import com.ihab.e_commerce.data.mapper.ProductMapper;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.data.repo.UserRepository;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.rest.response.WishlistResponse;
import com.ihab.e_commerce.service.product.ProductService;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    /*------------------------ wishlist functionality -----------------------*/

    /* todo consider returning the count only no need to return the entire list */
    public WishlistResponse addToWishlist(Long productId) {
        User user = userService.loadCurrentUser();
        Product product = productService.getProductForOthersClasses(productId);
        user.addToWishlist(product);
        userRepository.save(user);
        Set<ProductResponse> productResponse = user.getWishList().stream().map(
                productMapper::fromProductToProductResponse
        ).collect(Collectors.toSet());
        return WishlistResponse.builder()
                .count(user.getWishList().size())
                .productResponseSet(productResponse)
                .build();
    }


    // todo maybe merging those two method
    public WishlistResponse getWishList() {
        User user = userService.loadCurrentUser();
        Set<ProductResponse> productResponse = user.getWishList().stream().map(
                productMapper::fromProductToProductResponse
        ).collect(Collectors.toSet());
        return WishlistResponse.builder()
                .count(user.getWishList().size())
                .productResponseSet(productResponse)
                .build();
    }

    public WishlistResponse removeFromWishlist(Long productId) {
        User user = userService.loadCurrentUser();
        Product product = productService.getProductForOthersClasses(productId);
        user.removeFromWishlist(product);
        userRepository.save(user);
        Set<ProductResponse> productResponse = user.getWishList().stream().map(
                productMapper::fromProductToProductResponse
        ).collect(Collectors.toSet());
        return WishlistResponse.builder()
                .count(user.getWishList().size())
                .productResponseSet(productResponse)
                .build();
    }
}
