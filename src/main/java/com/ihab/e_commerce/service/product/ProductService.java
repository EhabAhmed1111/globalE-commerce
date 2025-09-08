package com.ihab.e_commerce.service.product;


import com.ihab.e_commerce.controller.response.ProductResponse;
import com.ihab.e_commerce.data.dto.ProductDto;
import com.ihab.e_commerce.data.mapper.ProductMapper;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.ProductRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    final private ProductRepo productRepo;
    final private ProductMapper productMapper;
    final private CategoryService categoryService;


    public ProductResponse getProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new GlobalNotFoundException(" There is no product with id: " + productId)
        );
        return productMapper.fromProductToProductResponse(product);
    }


    public ProductResponse addProduct(ProductDto productDto) {
        Product product = productMapper.fromDtoToProduct(productDto);
        productRepo.save(product);
        return productMapper.fromProductToProductResponse(product);
    }


    public void deleteProduct(Long productId) {
        productRepo.findById(productId).ifPresentOrElse(productRepo::delete,
                () -> {
                    throw new GlobalNotFoundException(" There is no product with id: " + productId);
                });
    }

    // Should I add this

    public ProductResponse updateProduct(ProductDto productDto, Long productId){
        Product product = productRepo.findById(productId).orElseThrow(() -> new GlobalNotFoundException(" There is no product with id: " + productId)
        );
        Product updatedProduct = productRepo.save(updateProduct(productDto, product));
        return productMapper.fromProductToProductResponse(updatedProduct);
    }
        private Product updateProduct(ProductDto productDto, Product product){
            Category category = categoryService.getCategoryByName(productDto.getCategoryName());

            product.setAmount(productDto.getAmount());
            product.setName(productDto.getProductName());
            product.setBrand(productDto.getBrand());
            product.setCategory(category);
            product.setDescription(product.getDescription());
            product.setPrice(productDto.getPrice());

            return product;
        }
}
