package com.ihab.e_commerce.rest.controller.order;


import com.ihab.e_commerce.data.model.Order;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<GlobalSuccessResponse> createOrder(){
        Order order = orderService.makeOrder();
        return ResponseEntity.ok(new GlobalSuccessResponse("Order made successfully", order));
    }

    @PostMapping("/{userId}/re-order")
    public ResponseEntity<GlobalSuccessResponse> reOrderLastOrder(@PathVariable Long userId){
        Order order = orderService.reOrderLastOrder();
        return ResponseEntity.ok(new GlobalSuccessResponse("Order reMade successfully", order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GlobalSuccessResponse> getSpecificOrder(@PathVariable Long orderId){
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new GlobalSuccessResponse("Order fetched successfully", order));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GlobalSuccessResponse> getAllUserOrders(@PathVariable Long userId){
        List<Order> orders = orderService.getAllOrderForUser(userId);
        return ResponseEntity.ok(new GlobalSuccessResponse("Order fetched successfully", orders));
    }


    // todo(update and delete end point)
}
