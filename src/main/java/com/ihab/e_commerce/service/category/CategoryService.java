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
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {

        return categoryRepo.findById(id).orElseThrow(() -> new GlobalNotFoundException("There is no category with id: " + id)
        );
    }

    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name).orElseThrow(() -> new GlobalNotFoundException("There is no category with name: " + name)
        );
    }


    // only ADMIN Role
    public Category updateCategory(Category category, Long currentCategoryId) {
        Category existedCategory = getCategoryById(currentCategoryId);
        existedCategory.setName(category.getName());
        return categoryRepo.save(existedCategory);
    }

    public Category deleteCategoryById(Long id) {
        Category existedCategory = getCategoryById(id);
        categoryRepo.delete(existedCategory);
        return existedCategory;
    }

    public Category addCategory(Category category) {
        // Maybe I could add check case to check if their another category with same name?
        // should I add exception in case there is null value
        return categoryRepo.save(category);
    }
}
