package com.ihab.e_commerce.service.product;

import com.ihab.e_commerce.data.dto.ProductDto;
import com.ihab.e_commerce.data.mapper.ProductMapper;
import com.ihab.e_commerce.data.model.Media;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.ProductRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.service.category.CategoryService;
import com.ihab.e_commerce.service.media.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private MediaService mediaService;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;


    @Test
    void getProduct_ShouldReturnProduct_WhenIdMatch() {
        // Given
        Product product = Product.builder()
                .id(1L)
                .name("Phone")
                .price(new BigDecimal(2000))
                .brand("Iphone")
                .amount(5)
                .build();
        ProductResponse productResponse = productMapper.fromProductToProductResponse(product);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));


        //When
        ProductResponse actualResponse = productService.getProduct(1L);

        //Then
        assertEquals(productResponse, actualResponse);
    }

    @Test
    void getProduct_ShouldThrowException_WhenIdMisMatch() {
        //Given
        Long id = 999L;
        when(productRepo.findById(id)).thenReturn(Optional.empty());

        //When
        //Then
        GlobalNotFoundException exception = assertThrows(GlobalNotFoundException.class,
                () -> {
                    productService.getProduct(id);
                });
        String actualMessage = exception.getMessage();
        String expectedMessage = " There is no product with id: " + id;

        assertEquals(actualMessage, expectedMessage);
    }

//    @Test
//    void addProduct_ShouldAddNewProduct() {
//        //Given
//        ProductDto productDto = ProductDto.builder()
//                .productName("phone")
//                .price(new BigDecimal(2000))
//                .amount(5)
//                .brand("Iphone")
//                .categoryName("Electronic")
//                .build();
//        Product product = productMapper.fromDtoToProduct(productDto);
//        ProductResponse productResponse = productMapper.fromProductToProductResponse(product);
//        when(productRepo.save(product)).thenReturn(product);
//
//        // When
//        ProductResponse actualResponse = productService.addProduct(productDto);
//
//        // Then
//        assertEquals(productResponse, actualResponse);
//    }

    @Test
    @Disabled
    void deleteProduct_ShouldDeleteProduct_WhenIdExist() {
        //Given
        Product product = Product.builder()
                .id(1L)
                .name("phone")
                .price(new BigDecimal(2000))
                .build();
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(mediaService).deleteFile(anyString());
        doNothing().when(productRepo).delete(product);
        // When
        productService.deleteProduct(product.getId());
        // Then
    }

    @Test
    @Disabled
    void deleteProduct_ShouldThrowRunTimeException_WhenDeletionFailed() {
        //Given
        Media media = Media.builder()
                .id(1L)
                .cloudinaryPublicId("media_public_id_1")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("phone")
                .media(Collections.singletonList(media))
                .price(new BigDecimal(2000))
                .build();
        RuntimeException cloudinaryException = new RuntimeException("Cloudinary connection failed");

        // It did this because I need to test failed in file
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        doThrow(cloudinaryException).when(mediaService).deleteFile("media_public_id_1");
        // When
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> {
            productService.deleteProduct(1L);
                });
        // Then

        assertEquals(cloudinaryException, exception.getCause());
    }

    @Test
    void updateProduct_ShouldUpdateProduct_whenIdExist(){
        Product product = Product.builder()
                .id(1L)
                .name("phone")
                .price(new BigDecimal(2000))
                .build();
        ProductDto productDto = ProductDto.builder()    
                .productName("phoneTest")
                .price(new BigDecimal(3000))
                .build();

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(product))
                .thenAnswer(invocation ->invocation.getArgument(0));
when(productMapper.fromProductToProductResponse(any(Product.class)));
        ProductResponse actualResponse = productService.updateProduct(productDto, 1L);

        assertEquals(productDto.getProductName(), actualResponse.getProductName());
    }

}