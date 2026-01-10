package com.ihab.e_commerce.service.cart;

import com.ihab.e_commerce.data.mapper.CartMapper;
import com.ihab.e_commerce.data.model.Cart;
import com.ihab.e_commerce.data.model.CartItem;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.data.repo.CartItemRepo;
import com.ihab.e_commerce.data.repo.CartRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.rest.response.CartResponse;
import com.ihab.e_commerce.service.product.ProductService;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
// todo(if cart isActive is false there should be another cart)
    private final CartRepo cartRepo;
    private final UserService userService;
    private final CartItemRepo cartItemRepo;
    private final ProductService productService;
    private final CartMapper cartMapper;


// Creating OP
    public Cart initializeCart(Long userId) {
        User user = userService.getUserById(userId);
        return Optional.ofNullable(getCartByItsActivationForCurrentUser(true)).orElseGet(
                () -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                }
        );
    }

    // Adding OP
    public CartResponse addingProductToCart(Long productId, int quantity) {

        Cart cart = getCartByItsActivationForCurrentUser(true);
        Product product = productService.getProductForOthersClasses(productId);

        cart.getCartItems()
                .stream()
                .filter(
                        item -> item.getProduct().equals(product)
                )
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                           Integer amountOfProduct = quantity + item.getQuantity() <= product.getAmount() ? item.getQuantity() + quantity: product.getAmount();
                                item.setQuantity(amountOfProduct);
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
        Cart savedCart =  cartRepo.save(cart);

        return cartMapper.fromCartToResponse(savedCart);
    }

    // Updating OP
    public CartResponse updatingCartItem(Long productId, int quantity) {
        Product product = productService.getProductForOthersClasses(productId);
        Cart cart = getCartByItsActivationForCurrentUser(true);
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
        return cartMapper.fromCartToResponse(cartRepo.save(cart));
    }


    // Reading OP
    // this for endpoint
    public CartResponse getCurrentCart() {
        return cartMapper.fromCartToResponse(getCartByItsActivationForCurrentUser(true));
    }

    public Cart getCartByItsActivationForCurrentUser(Boolean isActive) {
        /* todo i need to make this return all active cart only
        *   */
//        List<Cart> carts = getAllCartByUserId(user.getId());
//        Cart cart = carts.stream().filter(
//                expectedCart -> expectedCart.getIsActive() == false
//        ).findFirst().orElseThrow(
//                () -> new GlobalNotFoundException("There is no previous order for you")
//        );
        User user = userService.loadCurrentUser();
        return cartRepo.findByUserIdAndIsActive(user.getId(), isActive).orElseGet(
                () -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                }
        );
    }

    public List<Cart> getAllCartByUserId(Long userId) {
        return cartRepo.findAllByUserId(userId);
    }

    public Cart getCartById(Long cartId) {

        if (cartId == null) {
            Cart cart = new Cart();
            cart.setUser(userService.loadCurrentUser());
            return cartRepo.save(cart);
        }
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
    // todo this need edit
    public void clearCart(Long cartId) {
        // it more sensible to let it with cartId
        Cart cart = getCartById(cartId);
        cartItemRepo.deleteAllByCartId(cartId);
        cart.getCartItems().clear();
        cartRepo.delete(cart);
    }

    public CartResponse deleteCartItemFromCart(Long cartItemId) {
        Cart cart = getCartByItsActivationForCurrentUser(true);
        CartItem cartItem = getCartItemFromCart(cart.getId(), cartItemId);
        cart.removeItem(cartItem);

        // This will delete cart item by cascading future
       return cartMapper.fromCartToResponse(cartRepo.save(cart));
    }
}
