package com.bloggApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private long categoryId;

    @NotEmpty
    @Size(min = 4, message = "categoryTittle must be min of 4 characters")
    private String categoryTittle;

    @NotEmpty
    @Size(min = 10, message = "categoryDescription  must be min of 10 characters")
    @Size(max = 2000, message = "categoryDescription must be max of 2000 characters")
    private String categoryDescription;


}