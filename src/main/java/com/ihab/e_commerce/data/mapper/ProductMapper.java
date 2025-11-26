package com.ihab.e_commerce.data.mapper;

import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.data.dto.MediaDto;
import com.ihab.e_commerce.data.dto.ProductDto;
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

    public Product fromDtoToProduct(ProductDto productDto) {
        Category category = categoryService.getCategoryByName(productDto.getCategoryName());
        return Product.builder()
                .name(productDto.getProductName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(category)
                .brand(productDto.getBrand())
                .amount(productDto.getAmount())
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

