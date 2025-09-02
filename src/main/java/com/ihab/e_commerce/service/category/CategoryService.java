package com.ihab.e_commerce.service.category;


import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.repo.CategoryRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
    //todo crud op
    /*
     * create --> only admin can create category
     * update -->
     * delete
     * read*/
    /*
     * Role   create    update   read      delete
     * ADMIN   yes        yes     yes         yes
     * USER     no        no      yes         no
     */
    private final CategoryRepo categoryRepo;


    // ADMIN, USER Role
    public List<Category> getAllCategories() {
        log.debug("Fetching all category from getAllCategories method");
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {
        log.debug("Fetching category with id: {} from getCategoryById method", id);

        return categoryRepo.findById(id).orElseThrow(() -> {
            log.warn("there is no category with id: {} from getCategoryById method", id);
            return new GlobalNotFoundException("There is no category with id: " + id);
                }
        );
    }

    public Category getCategoryByName(String name) {
        log.debug("Fetching category with name: {} from getCategoryByName method", name);
        return categoryRepo.findByName(name).orElseThrow(() -> {
                    log.warn("there is no category with name: {} from getCategoryByName method", name);
                    return new GlobalNotFoundException("There is no category with name: " + name);
                }
        );
    }


    // only ADMIN Role
    public Category updateCategory(Category category, Long currentCategoryId) {
        log.debug("Fetching category with id: {} from updateCategory method", currentCategoryId);
        Category existedCategory = getCategoryById(currentCategoryId);
        log.info("Modifying category with name: {} from updateCategory method", currentCategoryId);
        existedCategory.setName(category.getName());
        log.info("Successfully modifying category to name: {} from updateCategory method", currentCategoryId);
        return categoryRepo.save(existedCategory);
    }

    public Category deleteCategoryById(Long id) {
        log.debug("Deleting category with id: {} from deleteCategoryById method", id);
        Category existedCategory = getCategoryById(id);
        categoryRepo.delete(existedCategory);
        log.info("Successfully delete category with id: {} from deleteCategoryById method", id);
        return existedCategory;
    }

    public Category addCategory(Category category) {
        // should I add exception in case there is null value
        log.debug("Adding category with name: {} from addCategory method", category.getName());
        return categoryRepo.save(category);
    }
}
