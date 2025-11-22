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
@Table(name = "media")
public class Media {
   /* todo here there should be a cover photo it will be here to avoid circular dep
   * i will make it boolean property(isCover)
    * */

    /*
    * ok new plan I decided to use cloudinary as my storage
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We deal with this later
    @Column(name = "url")
    private String url;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "cloudinary_public_id", unique = true)
    private String cloudinaryPublicId; // Important for deletion

    /*-- product cover image ---*/
    @Column(name="is_cover")
    private Boolean isCoverImage = false;

    @CreationTimestamp
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
