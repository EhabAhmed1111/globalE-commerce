package com.ihab.e_commerce.service.product;


import com.ihab.e_commerce.data.mapper.ProductMapper;
import com.ihab.e_commerce.data.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    final private ProductRepo productRepo;
    final private ProductMapper productMapper;

    /*
     * In order to create this I have to create Media first
     */
}
