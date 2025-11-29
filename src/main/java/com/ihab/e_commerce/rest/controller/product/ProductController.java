package com.ihab.e_commerce.rest.controller.product;

import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.data.dto.ProductDto;
import com.ihab.e_commerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;
//todo get all product
//todo get all product with category

    @GetMapping("/{id}")
    public ResponseEntity<GlobalSuccessResponse> getProductWithId(@PathVariable Long id) {
        ProductResponse product = productService.getProduct(id);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", product));
    }
    @GetMapping()
    public ResponseEntity<GlobalSuccessResponse> getAllProduct() {
        List<ProductResponse> products = productService.getAllProduct();
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", products));
    }

    /* here we get all product by vendor id */
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<GlobalSuccessResponse> getAllProduct(
            @PathVariable Long vendorId
    ) {
        List<ProductResponse> products = productService.getAllProductByVendorId(vendorId);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", products));
    }

    @PostMapping
    // no need for admin to create product
    @PreAuthorize("(hasRole('VENDOR')) and (hasAuthority('vendor:create'))")
    public ResponseEntity<GlobalSuccessResponse> addProduct(@RequestBody ProductDto productDto) {
        ProductResponse product = productService.addProduct(productDto);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(hasAnyRole('ADMIN', 'VENDOR')) and (hasAnyAuthority('admin:delete', 'vendor:delete'))")
    public ResponseEntity<GlobalSuccessResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", null));
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('VENDOR')) and (hasAuthority('vendor:update'))")
    public ResponseEntity<GlobalSuccessResponse> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        ProductResponse product = productService.updateProduct(productDto, id);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", product));
    }
}
