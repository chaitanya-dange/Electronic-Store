package com.electronicStore.controller;

import com.electronicStore.dtos.CategoryDto;
import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.dtos.ProductDto;
import com.electronicStore.services.CategoryService;
import com.electronicStore.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

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

    //create product with category
    @PostMapping({"/{categoryId}/product"})
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
            ) {
        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);
        return  new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }

    // update product with category ID

    @PutMapping({"/{categoryId}/product/{productId}"})
    public  ResponseEntity<ProductDto> updateProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("productId") String productId
    ){
        ProductDto productDto = productService.updateProductWithCategory(productId, categoryId);
        return ResponseEntity.ok(productDto);
    }

    // list of products from particular category object.
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsFromCategory(
            @PathVariable(value = "categoryId") String categoryId,@RequestParam( value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam( value = "sortBy",defaultValue ="title",required = false) String sortBy,
            @RequestParam( value = "sortDir",defaultValue ="asc",required = false) String sortDir
    ){
        PageableResponse<ProductDto> allProductsOfCategory = productService.getAllProductsOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(allProductsOfCategory);

    }


}
