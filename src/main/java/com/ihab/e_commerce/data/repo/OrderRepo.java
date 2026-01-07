package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findAllByBuyerId(Long buyerId);
}
