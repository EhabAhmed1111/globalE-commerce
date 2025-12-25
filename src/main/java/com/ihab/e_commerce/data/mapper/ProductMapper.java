package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.rest.request.ProductRequest;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.data.dto.MediaDto;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.model.Media;
import com.ihab.e_commerce.data.model.Product;
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

    private final UserMapper userMapper;

    public Product fromDtoToProduct(ProductRequest productRequest) {
        Category category = categoryService.getCategoryByName(productRequest.getCategoryName());
        return Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(category)
                .brand(productRequest.getBrand())
                .amount(productRequest.getAmount())
                .build();
    }

    public ProductRequest fromProductToDto(Product product){
        Category category = product.getCategory();
        return ProductRequest.builder()
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
        // here we get user to map it to dto
        User user = product.getVendor();
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .categoryName(category.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .medias(mediaMapper.fromListOfMediaToListOfDto(medias))
                .vendor(userMapper.fromUserToDto(user))
                .avgRate(product.getAvgRate())
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
                .avgRate(productResponse.getAvgRate())
                .amount(productResponse.getAmount())
                .price(productResponse.getPrice())
                .media(mediaMapper.fromDtoToListOfMedia(medias))
                .build();
    }
}

