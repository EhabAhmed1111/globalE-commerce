package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    // todo maybe we create method that get cart with user id and it Active condition
    Optional<Cart> findByUserId(Long userId);

    // this will get cart based on its activation
    Optional<Cart> findByUserIdAndIsActive(Long userId, boolean isActive);

    List<Cart> findAllByUserId(Long userId);

//
//    @Query("SELECT c FROM Cart c " +
//            "WHERE c.user.id = :userId " +
//            "AND c.isActive = false " +
//            "ORDER BY c.createdAt DESC")
//    Optional<Cart> findLatestInactiveCart(@Param("userId") Long userId);
}
