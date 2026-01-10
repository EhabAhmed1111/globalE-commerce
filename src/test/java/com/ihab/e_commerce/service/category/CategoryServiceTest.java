package com.ihab.e_commerce.service.category;

import com.ihab.e_commerce.data.model.Category;
import com.ihab.e_commerce.data.repo.CategoryRepo;
import com.ihab.e_commerce.exception.GlobalConflictException;
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
    void addCategory_shouldAddNewCategoryAndReturnIt_whenThereIsNoCategoryWithSameName() {
        // Given
        Category expectedCategory = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.save(expectedCategory)).thenReturn(expectedCategory);
        when(categoryRepo.findByName(expectedCategory.getName())).thenReturn(Optional.empty());

        // When
        Category actualCategory = categoryService.addCategory(expectedCategory);


        // Then
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void addCategory_shouldReturnTheExistingOne_whenItHasSameName() {
        // Given
        Category expectedCategory = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findByName(expectedCategory.getName())).thenReturn(Optional.of(expectedCategory));

        // When
        Category actualCategory = categoryService.addCategory(expectedCategory);


        // Then
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void getAllCategories_shouldReturnAllCategory_whenThereAreCategories() {
        // Given
        List<Category> expectedCategories = List.of(
                Category.builder()
                        .id(1L)
                        .name("Electronic")
                        .build());

        when(categoryRepo.findAll()).thenReturn(expectedCategories);

        // When
        List<Category> actualCategories = categoryService.getAllCategories();


        // Then
        assertEquals(expectedCategories, actualCategories);

    }

    @Test
    void getAllCategories_shouldReturnEmptyList_whenThereIsNoCategory() {

        // Given
        when(categoryRepo.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Category> actualCategories = categoryService.getAllCategories();

        // Then
        assertTrue(actualCategories.isEmpty());

    }

    @Test
    void getCategoryById_shouldReturnCategory_whenIdIsCorrect() {
        // Given
        Category expectedCategory = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findById(expectedCategory.getId())).thenReturn(Optional.of(expectedCategory));

        // When
        Category actualCategory = categoryService.getCategoryById(expectedCategory.getId());

        // Then
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void getCategoryById_shouldThrowException_whenIdIsIncorrect() {
        // Given
        Long nonExistedId = 999L;
        when(categoryRepo.findById(nonExistedId)).thenReturn(Optional.empty());

        // When
        // Then
        GlobalNotFoundException exception = assertThrows(GlobalNotFoundException.class,
                () -> categoryService.getCategoryById(nonExistedId)
        );
        String actualMessage = exception.getMessage();
        String expectedMessage = "There is no category with id: " + nonExistedId;

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getCategoryByName_shouldReturnCategory_whenNameIsCorrect() {
        // Given
        Category expectedCategory = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findByName(expectedCategory.getName())).thenReturn(Optional.of(expectedCategory));

        // When
        Category actualCategory = categoryService.getCategoryByName("Electronic");


        // Then
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void getCategoryByName_shouldThrowException_whenNameIsIncorrect() {
        // Given
        String nonExistedName = "NonExistedCategory";

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


    // should I test it, it is only repo method
    @Test
    void deleteCategoryById_shouldDeleteCategory_whenIdExist() {
        // Given
        Category existedCategory = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        when(categoryRepo.findById(existedCategory.getId())).thenReturn(Optional.of(existedCategory));

        // When
        Category deletedCategory = categoryService.deleteCategoryById(1L);

        // Then
        verify(categoryRepo).delete(existedCategory);
        assertEquals(existedCategory, deletedCategory);

    }


    @Test
    void updateCategory_shouldUpdateCategory_whenNameOfCurrentCategoryIsNew() {
        // Given
        Category existedCategory = Category.builder()
                .id(1L)
                .name("Electronic")
                .build();

        Category request = Category.builder()
                .name("Phones")
                .build();

        when(categoryRepo.findById(existedCategory.getId())).thenReturn(Optional.of(existedCategory));
        when(categoryRepo.findByName(request.getName())).thenReturn(Optional.empty());

        /* so basically invocation is an object that contain all details about the method that called
         * in our case it's a save method*/
        when(categoryRepo.save(any(Category.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Category actualCategory = categoryService.updateCategory(request, existedCategory.getId());

        // Then
        assertEquals("Phones", actualCategory.getName());
    }

    @Test
    void updateCategory_shouldThrowException_whenNameOfCategoryInUpdateRequestIsAlreadyThere() {
        // given
        Category existedCategory = Category.builder()
                .id(1L)
                .name("Phones")
                .build();

        Category conflictingCategory = Category.builder()
                .id(1L)
                .name("Computers")
                .build();

        Category request = Category.builder()
                .name("Computers")
                .build();

        when(categoryRepo.findById(existedCategory.getId())).thenReturn(Optional.of(existedCategory));
        when(categoryRepo.findByName(request.getName())).thenReturn(Optional.of(conflictingCategory));

        // when
        // then
        GlobalConflictException exception = assertThrows(GlobalConflictException.class,
                () -> categoryService.updateCategory(request, existedCategory.getId()));
        String expectedExceptionMessage = "Category with name '" + conflictingCategory.getName() + "' already exists";


        assertEquals(expectedExceptionMessage, exception.getMessage());

    }

    // todo I need to add test about adding image to category
}