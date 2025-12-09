package com.ihab.e_commerce.rest.controller.user.main;

import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.rest.response.WishlistResponse;
import com.ihab.e_commerce.service.user.main.UserService;
import com.ihab.e_commerce.service.user.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WishlistService wishlistService;


    /* todo update all these method*/
    @GetMapping
    public ResponseEntity<GlobalSuccessResponse> getCurrentUser() {
        User user = userService.loadCurrentUser();
        return ResponseEntity.ok(new GlobalSuccessResponse("User found", user));
    }

    // This for admin
    @DeleteMapping("/{userId}")
    public ResponseEntity<GlobalSuccessResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new GlobalSuccessResponse("User deleted", null));
    }

    @PutMapping("/{currentUserId}")
    public ResponseEntity<GlobalSuccessResponse> updateUser(@PathVariable Long currentUserId, @RequestBody User updatReqUser) {
        User user = userService.updateUser(updatReqUser, currentUserId);
        return ResponseEntity.ok(new GlobalSuccessResponse("User updated successfully", user));
    }

    /*------------------ wishlist --------------------------*/
    @GetMapping("/wishlist")
    public ResponseEntity<GlobalSuccessResponse> getWishlistOfCurrentUser() {
        WishlistResponse wishlistResponse = wishlistService.getWishList();
        return ResponseEntity.ok(new GlobalSuccessResponse("Fetched wishlist successfully", wishlistResponse));
    }

    @PostMapping("/products/{productId}/wishlist")
    public ResponseEntity<GlobalSuccessResponse> addProductToWishlist(@PathVariable Long productId) {
        WishlistResponse wishlistResponse = wishlistService.addToWishlist(productId);
        return ResponseEntity.ok(new GlobalSuccessResponse("Product added to wishlist successfully", wishlistResponse));
    }

    @DeleteMapping("/products/{productId}/wishlist")
    public ResponseEntity<GlobalSuccessResponse> removeProductFromWishlist(@PathVariable Long productId) {
        WishlistResponse wishlistResponse = wishlistService.removeFromWishlist(productId);
        return ResponseEntity.ok(new GlobalSuccessResponse("product removed from wishlist successfully", wishlistResponse));
    }

    @GetMapping("/wishlist/favorite")
    public ResponseEntity<GlobalSuccessResponse> checkIfProductIsFavorite(@RequestParam Long productId) {
        Boolean isFavorite = wishlistService.checkIfProductIsFavorite(productId);
        return ResponseEntity.ok(new GlobalSuccessResponse("Check done", isFavorite));
    }
    // todo create more user functionality

}
