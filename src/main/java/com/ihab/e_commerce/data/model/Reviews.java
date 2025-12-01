package com.ihab.e_commerce.data.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review")
public class Reviews {
    /* todo
     *   we will start working on this feature tomorrow
     *   lets define our relation
     *   one for product it will be many to one
     *   and one for user it will be many to one
     *   it will have a content and double */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rating;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* user relation */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
