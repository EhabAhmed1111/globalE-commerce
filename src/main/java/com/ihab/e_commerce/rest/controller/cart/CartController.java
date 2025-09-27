package com.ihab.e_commerce.rest.controller.cart;


import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "${api.prefix}/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping("/users/{userId}/cart/initialize")
    public ResponseEntity<GlobalSuccessResponse> initializeCart(@PathVariable Long userId) {
        Cart cart = cartService.initializeCart(userId);
        return ResponseEntity.ok(new GlobalSuccessResponse("Initialize cart successfully", cart));
    }

    @PostMapping("/products/{productId}/adding-to-cart")
    public ResponseEntity<GlobalSuccessResponse> addingProductToCart(@PathVariable Long productId,
                                                                     @RequestParam(required = false) Long cartId,
                                                                     @RequestParam int quantity) {
// Here should be condition id cartId null to create another cart
        // but should this be here or in service
        Cart cart = cartService.addingProductToCart(productId, cartId, quantity);

        return ResponseEntity.ok(new GlobalSuccessResponse("Item added successfully", cart));

    }

    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<GlobalSuccessResponse> updateQuantityOfItem(@PathVariable Long cartId,
                                                                      @PathVariable Long productId,
                                                                      @RequestParam int quantity){
        Cart cart = cartService.updatingCartItem(cartId, productId, quantity);
        return ResponseEntity.ok(new GlobalSuccessResponse("Cart updated successfully", cart));

    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<GlobalSuccessResponse> clearCart(@PathVariable Long cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new GlobalSuccessResponse("Cart cleared successfully", null));
    }

    @DeleteMapping("/{cartId}/cartItems/{cartItemId}")
    public ResponseEntity<GlobalSuccessResponse> removeCartItemFromCart(@PathVariable Long cartId, @PathVariable Long cartItemId){
        cartService.deleteCartItemFromCart(cartId, cartItemId);
        return ResponseEntity.ok(new GlobalSuccessResponse("CartItem deleted successfully", null));
    }

}
