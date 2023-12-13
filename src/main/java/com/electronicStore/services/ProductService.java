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
}
