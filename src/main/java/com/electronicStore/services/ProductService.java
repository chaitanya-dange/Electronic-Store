package com.electronicStore.services;

import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto,String productId);

    //delete
    void delete(String productId);

    //get single product
    ProductDto getSingleProduct(String productId);

    //get all product
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize , String sortBy, String sortDir);

    // get all live product
    PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize , String sortBy, String sortDir);

    // search products
    PageableResponse<ProductDto> searchByTitle(String productId,int pageNumber, int pageSize , String sortBy, String sortDir);


    //other products methods
    // create product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);

    // update category of product
    ProductDto updateProductWithCategory(String productId, String categoryId);

    // get all products of particular category object
    PageableResponse<ProductDto> getAllProductsOfCategory(String categoryId,int pageNumber, int pageSize , String sortBy, String sortDir);



}
