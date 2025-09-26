package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    public void deleteAllByCartId(Long cartId);
}
