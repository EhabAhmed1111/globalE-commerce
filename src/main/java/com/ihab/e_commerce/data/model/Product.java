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
     2-product owner
     3-images
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private BigDecimal price;

    private String brand;

    private Integer amount;

    private String description;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // The project may have more than one media, but it will have only one dir
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "product")
    private List<Media> media;


}
