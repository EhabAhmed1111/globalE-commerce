package com.ihab.e_commerce.service.cart;

import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.data.model.CartItem;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.data.repo.CartItemRepo;
import com.ihab.e_commerce.data.repo.CartRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.service.product.ProductService;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
// todo(make addProduct to try to find cart if its not exist we could create another cart)
    private final CartRepo cartRepo;
    private final UserService userService;
    private final CartItemRepo cartItemRepo;
    private final ProductService productService;


// Creating OP
    public Cart initializeCart(Long userId) {
        User user = userService.getUserById(userId);
        return Optional.ofNullable(getCartByUserId(userId)).orElseGet(
                () -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                }
        );
    }

    // Adding OP
    public Cart addingProductToCart(Long productId, Long cartId, int quantity) {

        Cart cart = getCartById(cartId);
        Product product = productService.getProductForOthersClasses(productId);

        cart.getCartItems()
                .stream()
                .filter(
                        item -> item.getProduct().equals(product)
                )
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(item.getQuantity() + quantity <= product.getAmount() ? item.getQuantity() + quantity : product.getAmount());
                            item.setTotalPrice();
                            cartItemRepo.save(item);
                        },
                        () -> {
                            CartItem item = CartItem.builder()
                                    .cart(cart)
                                    .product(product)
                                    .quantity(quantity <= product.getAmount() ? quantity : product.getAmount())
                                    .unitePrice(product.getPrice())
                                    .build();
                            item.setTotalPrice();
                            cart.addItem(item);
                            cartItemRepo.save(item);
                        }
                );

//       cartItem.setTotalPrice(cartItem.getUnitePrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cart.updateTotalPrice();
        return cartRepo.save(cart);
    }

    // Updating OP
    public Cart updatingCartItem(Long cartId, Long productId, int quantity) {
        Product product = productService.getProductForOthersClasses(productId);
        Cart cart = getCartById(cartId);
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            if (quantity <= product.getAmount()) {
                                item.setQuantity(quantity);
                            } else {
                                item.setQuantity(product.getAmount());
                            }
                            item.setTotalPrice();
                        },
                        () -> {
                            throw new GlobalNotFoundException("There is no product with id: " + productId + " in cart");
                        }
                );
        cart.updateTotalPrice();
        return cartRepo.save(cart);
    }


    // Reading OP
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }

    public Cart getCartById(Long cartId) {
        return cartRepo.findById(cartId).orElseGet(
                () -> {
                    // If cart not exist
                    Cart cart = new Cart();
                    cart.setUser(userService.loadCurrentUser());
                    return cartRepo.save(cart);
                }
        );
    }

    public CartItem getCartItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = getCartById(cartId);
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemId)).findFirst().orElseThrow(
                        () -> new GlobalNotFoundException("There is no cartItem with id: " + cartItemId)
                );
    }

    // Delete OP
    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cartItemRepo.deleteAllByCartId(cartId);
        cart.getCartItems().clear();
        cartRepo.delete(cart);
    }

    public void deleteCartItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = getCartById(cartId);
        CartItem cartItem = getCartItemFromCart(cartId, cartItemId);
        cart.removeItem(cartItem);

        // This will delete cart item by cascading future
        cartRepo.save(cart);
    }
}
