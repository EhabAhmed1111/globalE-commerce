package com.ihab.e_commerce.data.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {
    /*
    there must be
     1-reviews
     2-product owner done
     3-images done
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String brand;

    private Integer amount;

    private String description;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // The product may have more than one media, but it will have only one dir
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "product")
    private List<Media> media;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User vendor;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    // to put the oldest on top
    @OrderBy("createdAt DESC")
    private List<Reviews> reviews;

    // we calculate this one
    @Column(name = "avg_rating")
    private Double avgRate = 0.0;


    public void calcAvgRating() {
        if (reviews == null || reviews.isEmpty()) {
            return;
        }
        reviews.stream()
                .mapToInt(Reviews::getRating)
                .average()
                .ifPresent(avg -> this.avgRate = avg);
    }

}
