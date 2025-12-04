package com.ihab.e_commerce.service.category;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.repo.CategoryRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private final Cloudinary cloudinary;


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
//        Category category = categoryMapper.fromDtoToCategory(categoryDto);
        return categoryRepo.save(category);
    }

    public Category addImageToCategory(MultipartFile file, Long categoryId){
        Category category = getCategoryById(categoryId);

        return uploadSingleImage(file, category);
    }

    private Category uploadSingleImage(MultipartFile file, Category category) {
        try {
            String uniqueId = UUID.randomUUID().toString();
            var option = ObjectUtils.asMap(
                    "public_id", "products/images/" + uniqueId,
                    "folder", "ecommerce",
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", false,
                    "resource_type", "image"
            );
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), option);
            category.setImageUrl((String) uploadResult.get("secure_url"));
            category.setCloudinaryPublicId((String) uploadResult.get("public_id"));

            return categoryRepo.save(category);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Category: " + file.getOriginalFilename(), e);
        }
    }
}
