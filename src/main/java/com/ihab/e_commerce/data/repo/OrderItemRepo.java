package com.ihab.e_commerce.data.repo;

import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.data.model.OrderItem;
import com.ihab.e_commerce.rest.response.OrderItemResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    // this will return all item that are in specific order and the vendor id is same
//    @EntityGraph(value = "with-name-and-order")
//    List<Order> findAllByOrderIdAndVendorId(Long orderId, Long vendorId);

    /* todo from here get order id and then if there are more than one order item inside the same order
    *   make aggregate function that will calculate the amount of those items and then er could group by
    *   order id and amount buyer name and OrderStatus */
    // this will return all item that is the vendor id is same
//    @EntityGraph(attributePaths = "vendor")
//    List<OrderItem> findAllByVendorId(Long vendorId);


    /* this will get the order(not all of it just id) id and amount of money that vendor has in this order
    if there are more than one order item in same order that have same order id
     it will sum and will become as 1 */
    @Query("SELECT new com.ihab.e_commerce.rest.response.OrderItemResponse("+
    "o.id, " +
    "SUM(oi.totalPrice), " +
    "o.createdAt, " +
    "o.orderStatus, "+
    "o.buyer.firstName || ' ' || o.buyer.lastName) " +
    "FROM OrderItem oi " +
    "JOIN oi.order o " +
    "JOIN oi.vendor v " +
    "WHERE v.id = :vendorId " +
    "GROUP BY o.id, o.createdAt, o.orderStatus, o.buyer.firstName, o.buyer.lastName")
    List<OrderItemResponse> getAllOrderItemForVendor(Long vendorId);

}
