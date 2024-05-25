package com.bloggApp.service.impl;

import com.bloggApp.dto.CategoryDTO;
import com.bloggApp.exceptions.ResourceNotFoundException;
import com.bloggApp.modal.Category;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.repository.CategoryRepository;
import com.bloggApp.service.CategoryService;
import com.bloggApp.utils.ModelMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMappers modelMappper;
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMappper.dtoToCategoryEntity(categoryDTO);
        Category savedCategory = categoryRepo.save(category);
        return modelMappper.entityToCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with this id :"+id));
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
       category.setCategoryTittle(categoryDTO.getCategoryTittle());
        Category updateCategory = categoryRepo.save(category);
        CategoryDTO categoryDTO1 = modelMappper.entityToCategoryDTO(updateCategory);
        return categoryDTO1;
    }

    @Override
    public List<CategoryDTO> findAllCategory() {
        return categoryRepo.findAll().stream().map(category -> modelMappper.entityToCategoryDTO(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
       Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with this id :" + id));
        return modelMappper.entityToCategoryDTO(category);
    }

    @Override
    public ApiResponse deleteCategory(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with this id :" + id));
        LocalDateTime dateTime =LocalDateTime.now();
        categoryRepo.delete(category);
        return new ApiResponse("Category deleted successfully with id " + id, true, dateTime.toLocalDate(), dateTime.toLocalTime());
    }
}
