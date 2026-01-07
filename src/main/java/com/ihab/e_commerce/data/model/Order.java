package com.ihab.e_commerce.data.model;


import com.ihab.e_commerce.data.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Payment> payments;

    public void addItems(Set<OrderItem> items) {
        orderItems.addAll(items);
    }
}
