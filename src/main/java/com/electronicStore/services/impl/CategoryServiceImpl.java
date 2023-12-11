package com.electronicStore.services.impl;

import com.electronicStore.dtos.CategoryDto;
import com.electronicStore.entities.Category;
import com.electronicStore.exceptions.ResourceNotFoundException;
import com.electronicStore.repository.CategoryRepository;
import com.electronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category categoryReceive = categoryRepository.save(category);

        return modelMapper.map(categoryReceive,CategoryDto.class);
    }


    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found exception"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategory = categoryRepository.findAll();

        List<CategoryDto> listOfDto = allCategory.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
        return listOfDto;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with id : {categoryId}"));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);

    }
}
