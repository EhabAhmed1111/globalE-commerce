package com.ihab.e_commerce.data.model;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice = BigDecimal.ZERO;

//    @Column(name = "is_active")
//    @BooleanFlag
//    private Boolean isActive = true;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name="creates_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

// We need to add method to update price when adding or removing

    public void addItem(CartItem item){
        this.cartItems.add(item);
//        item.setCart(this);
        updateTotalPrice();

    }

    public void removeItem(CartItem item){
        this.cartItems.remove(item);
//        item.setCart(this);
        updateTotalPrice();

    }

    public void updateTotalPrice() {
        this.totalPrice =
                this.cartItems
                        .stream()
                        .map(cartItems-> cartItems.getUnitePrice().multiply(BigDecimal.valueOf(cartItems.getQuantity())))
                        .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
