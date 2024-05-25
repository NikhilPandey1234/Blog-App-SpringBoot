package com.bloggApp.service;

import com.bloggApp.dto.CategoryDTO;
import com.bloggApp.payload.ApiResponse;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO updateCategory(Long id,CategoryDTO category);

    List<CategoryDTO> findAllCategory();

    CategoryDTO findCategoryById(Long id);

    ApiResponse deleteCategory(Long id);
}
