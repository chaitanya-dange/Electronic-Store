package com.electronicStore.services;

import com.electronicStore.dtos.CategoryDto;
import com.electronicStore.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {
    //create
    public CategoryDto createCategory(CategoryDto categoryDto);

    //update
    public  CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);


    // get all category
  List<CategoryDto> getAllCategory();

    // get category by id
    public CategoryDto getCategoryById(String categoryId);


    // delete category
    public void deleteCategory(String Id);
}
