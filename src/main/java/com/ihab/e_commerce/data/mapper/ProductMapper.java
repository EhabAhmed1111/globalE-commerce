package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.controller.response.ProductResponse;
import com.ihab.e_commerce.data.dto.MediaDto;
import com.ihab.e_commerce.data.dto.ProductDto;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.model.Media;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.CategoryRepo;
import com.ihab.e_commerce.service.category.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public class ProductMapper {

    /**@author IHAB
     * we will add image later
     */

    private final CategoryService categoryService;

    private final MediaMapper mediaMapper;

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
    public ProductResponse fromProductToProductResponse(Product product){
        Category category = product.getCategory();
        List<Media> medias = product.getMedia();

        return ProductResponse.builder()
                .productName(product.getName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .categoryName(category.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .medias(mediaMapper.fromListOfMediaToListOfDto(medias))
                .build();
    }
    public Product fromResponseToProduct(ProductResponse productResponse){
        Category category = categoryService.getCategoryByName(productResponse.getCategoryName());
        List<MediaDto> medias = productResponse.getMedias();

        return Product.builder()
                .name(productResponse.getProductName())
                .brand(productResponse.getBrand())
                .description(productResponse.getDescription())
                .category(category)
                .amount(productResponse.getAmount())
                .price(productResponse.getPrice())
                .media(mediaMapper.fromDtoToListOfMedia(medias))
                .build();
    }
}

