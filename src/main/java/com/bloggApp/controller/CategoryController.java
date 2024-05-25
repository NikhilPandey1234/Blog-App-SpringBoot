package com.bloggApp.controller;
import com.bloggApp.dto.CategoryDTO;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryDTOs = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(categoryDTOs, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable("categoryId") Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryDTOs = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getByIdCategory(@PathVariable("categoryId") Long categoryId) {
        CategoryDTO categoryDTOs = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
        return ResponseEntity.ok(categoryService.findAllCategory());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
}
