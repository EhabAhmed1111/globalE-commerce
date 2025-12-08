package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

    List<Cart> findAllByUserId(Long userId);
}
