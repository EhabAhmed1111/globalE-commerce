package com.ihab.e_commerce.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ihab.e_commerce.data.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
public class User implements UserDetails {

    /* todo make new field
        to identify the amount of money that each vendor made(monthly)
         (profits: BigDecimal)
         * how to calculate
         * each time any order made we go to product
         * then we will load the vendor from this product
         * then by getting vendor we will get the profits
         * then we use method to add the new profit with the previous one
         * it will be something like this
         * vendor.setProfits(vendor.getProfits + the new profits) */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    /* should I make it Cascade? yes */
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Product> products;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Cart> carts;

    // todo modify this cascade condition

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    private Set<Order> orders;

    /* todo
    *   this must be change
    *   instead of directly putting it here even if the OrderStatus is pending
    *   I will put value in it only if the OrderStatus is COMPLETE  */
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    private Set<OrderItem> soldOrderItems;


    @ManyToMany()
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    // we make it set to prevent duplicate
    private Set<Product> wishList;

    @OneToMany(mappedBy = "user")
    private Set<Payment> paymentSet;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private Set<Reviews> reviews;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /* get single auth it was ADMIN*/
//        return List.of(new SimpleGrantedAuthority(role.name()));
        /* git list of auth and role name */
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void addToWishlist(Product product) {
        this.wishList.add(product);
    }

    public void removeFromWishlist(Product product) {
        this.wishList.remove(product);
    }
}

