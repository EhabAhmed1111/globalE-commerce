package com.ihab.e_commerce.controller.category;


import com.ihab.e_commerce.controller.response.GlobalSuccessResponse;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.service.category.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<GlobalSuccessResponse> getAllCategory() {
        log.info("Received request for fetch All categories from getAllCategory method");
        List<Category> allCategory = categoryService.getAllCategories();
        log.info("Successfully retrieved All categories from getAllCategory method");
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "Successfully retrieved all categories",
                allCategory
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalSuccessResponse> findCategoryById(@PathVariable Long id) {
        log.info("Received request to fetch category with id: {} from findCategoryById method", id);
        Category category = categoryService.getCategoryById(id);
        log.info("Successfully retrieved category: {}  from findCategoryById method", category.getName());
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "Category Received successfully",
                category
        ));

    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<GlobalSuccessResponse> findCategoryByName(@PathVariable String categoryName) {
        log.info("Received request to fetch category with name: {} from findCategoryByName method", categoryName);
        Category category = categoryService.getCategoryByName(categoryName);
        log.info("Successfully retrieved category with name: {}  from findCategoryByName method", category.getName());
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "Category Received successfully",
                category
        ));
    }

    //ADMIN roles

    @PostMapping()
    public ResponseEntity<GlobalSuccessResponse> addCategory(HttpServletRequest request, @RequestBody Category category) {
        log.debug("Received a request to Add category with name: {} from addCategory method", category.getName());
        Category addedCategory = categoryService.addCategory(category);
        log.info("Successfully adding category with name: {}  from addCategory method", category.getName());
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "adding successfully",
                category
        ));
    }

    @PutMapping("/{currentCategoryId}")
    public ResponseEntity<GlobalSuccessResponse> updateCategory(
            @RequestBody Category category,
            @PathVariable Long currentCategoryId) {
        log.debug("Received a request to update category with id: {} from updateCategory method", currentCategoryId);
        Category updatedCategory = categoryService.updateCategory(category, currentCategoryId);
        log.info("Successfully updating category with name: {}  from updateCategory method", updatedCategory.getName());
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "updating successfully",
                updatedCategory
        ));
    }

    @DeleteMapping("/{currentCategoryId}")
    public ResponseEntity<GlobalSuccessResponse> deleteCategory(
            @PathVariable Long currentCategoryId) {
        log.debug("Received a request to delete category with id: {} from deleteCategory method", currentCategoryId);
        Category deletedCategory = categoryService.deleteCategoryById(currentCategoryId);
        log.info("Successfully updating category with name: {}  from deleteCategory method", deletedCategory.getName());
        return ResponseEntity.ok(new GlobalSuccessResponse(
                "updating successfully",
                deletedCategory
        ));
    }
}
