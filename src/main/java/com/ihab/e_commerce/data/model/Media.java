package com.ihab.e_commerce.data.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Media {

    /*
    * Here I need to make directory that has every thing about specific product
    * and when I need to access any file withing the dir
    * I can concatenate the downloadUrl with fileName
    * It will be like this
    * downloadUrl = "/home/moaz/springProject/cloth-shops-main-ideas/"
    * fileName = "Screenshot from 2025-07-10 23-34-37.png"
    * so the access URL will be
    * "/home/moaz/springProject/cloth-shops-main-ideas/Screenshot from 2025-07-10 23-34-37.png"
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String downloadUrl;

    private String fileType;

    private String fileName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
