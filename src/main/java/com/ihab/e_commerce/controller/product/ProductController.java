package com.ihab.e_commerce.controller.product;

import com.ihab.e_commerce.controller.response.GlobalSuccessResponse;
import com.ihab.e_commerce.controller.response.ProductResponse;
import com.ihab.e_commerce.data.dto.ProductDto;
import com.ihab.e_commerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;


    @GetMapping("/{id}")
    public ResponseEntity<GlobalSuccessResponse> getProductWithId(@PathVariable Long id) {
        ProductResponse product = productService.getProduct(id);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", product));
    }

    @PostMapping
    public ResponseEntity<GlobalSuccessResponse> addProduct(@RequestBody ProductDto productDto) {
        ProductResponse product = productService.addProduct(productDto);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalSuccessResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalSuccessResponse> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        ProductResponse product = productService.updateProduct(productDto, id);
        return ResponseEntity.ok(new GlobalSuccessResponse(" Product fetched successfully", product));
    }
}
