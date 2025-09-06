package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.data.dto.ProductDto;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.CategoryRepo;
import com.ihab.e_commerce.service.category.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductMapper {

    /**@author IHAB
     * we will add image later
     */

    private final CategoryService categoryService;

    public Product fromDtoToProduct(ProductDto productDto) {
        Category category = categoryService.getCategoryByName(productDto.getCategoryName());
        return Product.builder()
                .name(productDto.getProductName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(category)
                .brand(productDto.getBrand())
                .amount(productDto.getAmount())
                .category(category)
                .build();
    }

    public ProductDto fromProductToDto(Product product){
        Category category = product.getCategory();
        return ProductDto.builder()
                .productName(product.getName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .categoryName(category.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .build();
    }
}

