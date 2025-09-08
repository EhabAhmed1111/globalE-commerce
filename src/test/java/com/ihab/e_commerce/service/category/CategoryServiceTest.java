package com.ihab.e_commerce.service.category;

import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.repo.CategoryRepo;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {


    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllCategories_ShouldReturnAllCategory_WhenThereAreCategories() {
        // Given
        List<Category> categories = List.of(
                Category.builder()
                        .id(1L)
                        .name("Electronic")
                        .build());

        when(categoryRepo.findAll()).thenReturn(categories);
        // When
        List<Category> actualCategories = categoryService.getAllCategories();


        // Then
        assertEquals(categories, actualCategories);

    }

    @Test
    void getAllCategories_ShouldReturnEmptyList_WhenThereIsNoCategory() {
        // Given
        when(categoryRepo.findAll()).thenReturn(Collections.emptyList());
        // When
        List<Category> actualCategories = categoryService.getAllCategories();


        // Then
        assertTrue(actualCategories.isEmpty());

    }

    @Test
    void getCategoryById_ShouldReturnCategory_WhenIdIsCorrect() {
        // Given
        Category category = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findById(category.getId())).thenReturn(Optional.of(category));

        // When
        Category actualCategory = categoryService.getCategoryById(1L);


        // Then
        assertEquals(category, actualCategory);
    }

    @Test
    void getCategoryById_ShouldThrowException_WhenIdIsIncorrect() {
        // Given
        Long nonExistedId = 999L;
        Category category = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findById(nonExistedId)).thenReturn(Optional.empty());

        // When
        // Then
        GlobalNotFoundException exception = assertThrows(GlobalNotFoundException.class,
                () -> {
                    categoryService.getCategoryById(nonExistedId);
                });
        String actualMessage = exception.getMessage();
        String expectedMessage = "There is no category with id: " + nonExistedId;

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getCategoryByName_ShouldReturnCategory_WhenNameIsCorrect() {
        // Given
        Category category = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findByName(category.getName())).thenReturn(Optional.of(category));

        // When
        Category actualCategory = categoryService.getCategoryByName("Electronic");


        // Then
        assertEquals(category, actualCategory);
    }

    @Test
    void getCategoryByName_ShouldThrowException_WhenNameIsIncorrect() {
        // Given
        String nonExistedName = "NonExistedCategory";
        Category category = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findByName(nonExistedName)).thenReturn(Optional.empty());

        // When
        // Then
        GlobalNotFoundException exception = assertThrows(GlobalNotFoundException.class,
                () -> {
                    categoryService.getCategoryByName(nonExistedName);
                });
        String actualMessage = exception.getMessage();
        String expectedMessage = "There is no category with name: " + nonExistedName;

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void deleteCategoryById_ShouldDeleteCategory_WhenIdExist() {
        // Given
        Category category = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));

        // When
        Category deletedCategory = categoryService.deleteCategoryById(1L);

        // Then

        verify(categoryRepo).delete(category);
        assertEquals(category, deletedCategory);

    }


    // TODO() I need to know about Invocation
    @Test
    void updateCategory_ShouldUpdateCategory_WhenIdEqualCurrentCategoryId() {
        // Given
        Category category = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findById(category.getId())).thenReturn(Optional.of(category));
        /* To make this line clear
         * I can't use thenReturn because it will return the same Object every time
         * In update method I make the updates and then save
         * When I save the Repo return the argument that saved
         * so invocation is just container for method details it know what arg i save
         * then whatever I save it will return  */
        when(categoryRepo.save(any(Category.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Category excepectedCategory = Category.builder()
                .name("Phones")
                .build();

        Category actualCategory = categoryService.updateCategory(excepectedCategory, 1L);

        // Then
        assertEquals("Phones", actualCategory.getName());
    }

    @Test
    void addCategory_shouldAddAndReturnCategory() {
        // Given
        Category category = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.save(category)).thenReturn(category);

        // When
        Category actualCategory = categoryService.addCategory(category);


        // Then
        assertEquals(category, actualCategory);
    }
}