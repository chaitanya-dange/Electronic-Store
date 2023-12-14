package com.electronicStore.controller;

import com.electronicStore.dtos.ApiResponseMessage;
import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.dtos.ProductDto;
import com.electronicStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    //create
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto productDtoRtn = productService.create(productDto);
        return ResponseEntity.ok(productDtoRtn);
    }
    //update
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        ProductDto productDtoRtn = productService.update(productDto,productId);
        return ResponseEntity.ok(productDtoRtn);
    }
    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct (@PathVariable String productId){
        productService.delete(productId);
        ApiResponseMessage apiResponseMessage= new ApiResponseMessage();
        apiResponseMessage.builder().message("the product has been deleted successfully").success(true).build();

        return ResponseEntity.ok(apiResponseMessage);


    }

    // get all products
    @GetMapping()
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam( value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam( value = "sortBy",defaultValue ="title",required = false) String sortBy,
            @RequestParam( value = "sortDir",defaultValue ="asc",required = false) String sortDir
    ){
        PageableResponse<ProductDto> allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }

    // get a product by id
    @GetMapping ("/{productId}")
    public ResponseEntity<ProductDto> getProductById (@PathVariable String productId){
        ProductDto singleProduct = productService.getSingleProduct(productId);


        return ResponseEntity.ok(singleProduct);


    }


    // get all live products
    @GetMapping ("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProductList (
            @RequestParam( value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam( value = "sortBy",defaultValue ="title",required = false) String sortBy,
            @RequestParam( value = "sortDir",defaultValue ="asc",required = false) String sortDir){
        PageableResponse<ProductDto> allLiveProduct = productService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allLiveProduct);
    }

    //get all product search by title
    @GetMapping ("/search/{name}")
    public ResponseEntity<PageableResponse<ProductDto>> getSearchNameProductList (
            @RequestParam( value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam( value = "sortBy",defaultValue ="title",required = false) String sortBy,
            @RequestParam( value = "sortDir",defaultValue ="asc",required = false) String sortDir,
            @PathVariable String name){
        PageableResponse<ProductDto> allSearchProduct = productService.searchByTitle(name,pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(allSearchProduct);
    }
}
