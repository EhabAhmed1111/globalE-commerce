package com.ihab.e_commerce.service.product;


import com.ihab.e_commerce.data.enums.Role;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.exception.GlobalUnauthorizedActionException;
import com.ihab.e_commerce.rest.request.ProductRequest;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.data.mapper.ProductMapper;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.repo.ProductRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.service.category.CategoryService;
import com.ihab.e_commerce.service.media.MediaService;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ihab.e_commerce.data.enums.Role.ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    /*
     * TODO(ADD GET-ALL-PRODUCT-WITH-CATEGORY)
     *  and with price and with brand
     * get all product with vendor id
     * */

    final private CategoryService categoryService;
    private final MediaService mediaService;
    final private ProductRepo productRepo;
    final private ProductMapper productMapper;
    final private UserService userService;



    // this will work if I am vendor
    public List<ProductResponse> getAllProductForCurrentVendor() {
        User user = userService.loadCurrentUser();
        if (user.getRole() != Role.VENDOR) {
            throw new GlobalUnauthorizedActionException("This only for Vendors");
        }
        return userService
                .getUserById(user.getId())
                .getProducts()
                .stream()
                .map(productMapper::fromProductToProductResponse)
                .collect(Collectors.toList());
    }

    // this will work when I enter to the product
    public List<ProductResponse> getAllProductByVendorId(Long vendorId) {
        return userService
                .getUserById(vendorId)
                .getProducts()
                .stream()
                .map(productMapper::fromProductToProductResponse)
                .collect(Collectors.toList());
    }


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

    // I should add the id from token
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productMapper.fromDtoToProduct(productRequest);
        // here we got the user
        product.setVendor(userService.loadCurrentUser());
        productRepo.save(product);
        return productMapper.fromProductToProductResponse(product);
    }


    public void deleteProduct(Long productId) {
        Product product = getProductForOthersClasses(productId);
        User currentUser = userService.loadCurrentUser();


        if( product.getVendor() != currentUser) {
            log.info("Current user is not the vendor in deleteProductFunction");
            if (currentUser.getRole() != ADMIN ) {
                log.info("Current user is not admin too in deleteProductFunction");
                throw new GlobalUnauthorizedActionException("You are not allowed to delete this product ");
            }
        }
        log.info("Current user is either admin or the vendor of this product {} in deleteProductFunction", productId);

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

    public ProductResponse updateProduct(ProductRequest productRequest, Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new GlobalNotFoundException(" There is no product with id: " + productId)
        );
        User currentUser = userService.loadCurrentUser();
        if (product.getVendor() != currentUser) {
            throw new GlobalUnauthorizedActionException("You are not allowed to delete this product");
        }
        Product updatedProduct = productRepo.save(updateProduct(productRequest, product));
        return productMapper.fromProductToProductResponse(updatedProduct);
    }

    private Product updateProduct(ProductRequest productRequest, Product product) {
        Category category = categoryService.getCategoryByName(productRequest.getCategoryName());

        product.setAmount(productRequest.getAmount());
        product.setName(productRequest.getProductName());
        product.setBrand(productRequest.getBrand());
        product.setCategory(category);
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        return product;
    }
}
