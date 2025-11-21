package com.ihab.e_commerce.service.product;


import com.ihab.e_commerce.data.model.Media;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.data.dto.ProductDto;
import com.ihab.e_commerce.data.mapper.ProductMapper;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.ProductRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.service.category.CategoryService;
import com.ihab.e_commerce.service.media.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    /*
     * TODO(ADD GET-ALL-PRODUCT-WITH-CATEGORY)
     * */

    final private CategoryService categoryService;
    private final MediaService mediaService;
    final private ProductRepo productRepo;
    final private ProductMapper productMapper;

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepo.findAll();
        return products
                .stream()
                .map(productMapper::fromProductToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new GlobalNotFoundException(" There is no product with id: " + productId)
        );
        return productMapper.fromProductToProductResponse(product);
    }


    public Product getProductForOthersClasses(Long productId) {
        return productRepo.findById(productId).orElseThrow(() -> new GlobalNotFoundException(" There is no product with id: " + productId)
        );
    }


    public ProductResponse addProduct(ProductDto productDto) {
        Product product = productMapper.fromDtoToProduct(productDto);
        productRepo.save(product);
        return productMapper.fromProductToProductResponse(product);
    }


    public void deleteProduct(Long productId) {
        Product product = getProductForOthersClasses(productId);

        product.getMedia().forEach(
                media -> {
                    try {
                        mediaService.deleteFile(media.getCloudinaryPublicId());
                    } catch (Exception e) {
                        /*
                         * exception.getCause() = Exception
                         * why getCause()
                         * in method I catch Exception,
                         * but I wrapped it in runtime exception so basically
                         * the thrown exception will be runtime but the cause that make the runtime thrown will be the Exception which is IOException
                         * */
                        log.error("Failed to delete media from Cloudinary: {}",
                                media.getCloudinaryPublicId(), e);
                        throw new RuntimeException(e);
                    }
                }
        );
        productRepo.delete(product);

    }

    // Should I add this

    public ProductResponse updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new GlobalNotFoundException(" There is no product with id: " + productId)
        );
        Product updatedProduct = productRepo.save(updateProduct(productDto, product));
        return productMapper.fromProductToProductResponse(updatedProduct);
    }

    private Product updateProduct(ProductDto productDto, Product product) {
        Category category = categoryService.getCategoryByName(productDto.getCategoryName());

        product.setAmount(productDto.getAmount());
        product.setName(productDto.getProductName());
        product.setBrand(productDto.getBrand());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        return product;
    }
}
