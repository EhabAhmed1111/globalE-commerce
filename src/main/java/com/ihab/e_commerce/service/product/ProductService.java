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
        log.debug("Fetching product with id: {} from getProduct method", productId);
        Product product = productRepo.findById(productId).orElseThrow(() -> {
                    log.warn("there is no product with id: {} from getProduct method", productId);
                    return new GlobalNotFoundException(" There is no product with id: " + productId);
                }
        );
        log.info("Product Fetched successfully from getProduct method");
        return productMapper.fromProductToProductResponse(product);
    }


    public ProductResponse addProduct(ProductDto productDto) {
        log.debug("Adding product with name: {} from addProduct method", productDto.getProductName());
        Product product = productMapper.fromDtoToProduct(productDto);
        productRepo.save(product);
        log.info("Product added successfully from addProduct method");
        return productMapper.fromProductToProductResponse(product);
    }


    public void deleteProduct(Long productId) {
        log.debug("Deleting product with id: {} from deleteProduct method", productId);
        productRepo.findById(productId).ifPresentOrElse(productRepo::delete,
                () -> {
                    log.warn("Product with id: {} from method deleteProduct are not found", productId);
                    throw new GlobalNotFoundException(" There is no product with id: " + productId);
                });
    }

    // Should I add this

    public ProductResponse updateProduct(ProductDto productDto, Long productId){
        log.debug("Updating product with id: {} from updateProduct method", productId);
        Product product = productRepo.findById(productId).orElseThrow(() -> {
            log.warn("there is no product with id: {} from update method", productId);
            return new GlobalNotFoundException(" There is no product with id: " + productId);
                }
        );
        log.debug("Saving the updated product with id: {} from updateProduct method", productId);
        Product updatedProduct = productRepo.save(updateProduct(productDto, product));
        log.info("Saved done successfully from updateProduct method");
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
