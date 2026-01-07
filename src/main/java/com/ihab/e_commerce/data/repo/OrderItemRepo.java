package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.data.model.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    // this will return all item that are in specific order and the vendor id is same
    @EntityGraph(value = "with-name-and-order")
    List<Order> findAllByOrderIdAndVendorId(Long orderId, Long vendorId);

    // this will return all item that is the vendor id is same
    @EntityGraph(attributePaths = "vendor")
    List<OrderItem> findAllByVendorId(Long vendorId);

}
