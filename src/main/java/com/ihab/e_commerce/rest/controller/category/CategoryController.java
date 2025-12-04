package com.ihab.e_commerce.rest.controller.category;


import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<GlobalSuccessResponse> getAllCategory() {
        List<Category> allCategory = categoryService.getAllCategories();
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "Successfully retrieved all categories",
                allCategory
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalSuccessResponse> findCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "Category Received successfully",
                category
        ));

    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<GlobalSuccessResponse> findCategoryByName(@PathVariable String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "Category Received successfully",
                category
        ));
    }

    //ADMIN roles

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:create')")
    public ResponseEntity<GlobalSuccessResponse> addCategory(
            @RequestBody Category category
    ) {
        Category addedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "adding successfully",
                category
        ));
    }

    @PostMapping("/image/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:create')")
    public ResponseEntity<GlobalSuccessResponse> addImageToCategory(
            @PathVariable Long categoryId,
            @RequestParam("file") MultipartFile file
    ) {
        Category category = categoryService.addImageToCategory(file, categoryId);
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "adding image done successfully",
                category
        ));
    }

    @PutMapping("/{currentCategoryId}")
    public ResponseEntity<GlobalSuccessResponse> updateCategory(
            @RequestBody Category category,
            @PathVariable Long currentCategoryId) {
        Category updatedCategory = categoryService.updateCategory(category, currentCategoryId);
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "updating successfully",
                updatedCategory
        ));
    }

    @DeleteMapping("/{currentCategoryId}")
    public ResponseEntity<GlobalSuccessResponse> deleteCategory(
            @PathVariable Long currentCategoryId) {
        Category deletedCategory = categoryService.deleteCategoryById(currentCategoryId);
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "updating successfully",
                deletedCategory
        ));
    }
}
