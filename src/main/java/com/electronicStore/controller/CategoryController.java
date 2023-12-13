package com.electronicStore.controller;

import com.electronicStore.dtos.CategoryDto;
import com.electronicStore.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping()
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDtoRec = categoryService.createCategory(categoryDto);
        return  categoryDtoRec;
    }

    //update category
    @PutMapping ("/update/{categoryId}")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto , @PathVariable String categoryId){

        CategoryDto categoryDtoRec = categoryService.updateCategory(categoryDto,categoryId);
        return  categoryDtoRec;
    }
    //delete category
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
    }


    //getAll category
    @GetMapping()
    public List<CategoryDto> getAllCategory(){
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return allCategory;
    }


    // get category by Id
    @GetMapping("/{categoryId}")
    public  CategoryDto getCategoryById(@PathVariable String categoryId){
        CategoryDto category = categoryService.getCategoryById(categoryId);
        return  category;
    }


}
