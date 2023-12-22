package com.electronicStore.controller;

import com.electronicStore.dtos.*;
import com.electronicStore.services.FileService;
import com.electronicStore.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
@Tag(name = "ProductController" ,description = "API's for Product Module")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${products.image.path}")
    private String imagePath;
    //create
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto productDtoRtn = productService.create(productDto);
        return ResponseEntity.ok(productDtoRtn);
    }
    //update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        ProductDto productDtoRtn = productService.update(productDto,productId);
        return ResponseEntity.ok(productDtoRtn);
    }
    //delete
    @PreAuthorize("hasRole('ADMIN')")
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

    // uploading the image
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload/{productId}")
    public  ResponseEntity<ImageResponse> uploadProductImage(
            @RequestParam("productImage") MultipartFile image,
            @PathVariable String productId
            ) throws IOException {

        String uploadFileName = fileService.uploadFile(image, imagePath);
        ProductDto singleProduct = productService.getSingleProduct(productId);
        singleProduct.setProductImageName(uploadFileName);
        productService.update(singleProduct,productId);

        ImageResponse response = ImageResponse.builder().imageName(uploadFileName).success(true).message("successfully created..").build();
        return  new ResponseEntity<>(response,HttpStatus.CREATED);

    }


    // serve the image
    @GetMapping("/image/{productId}")
    public  void serverUserImage(@PathVariable String productId , HttpServletResponse response) throws IOException {
        ProductDto singleProduct = productService.getSingleProduct(productId);
        InputStream resource = fileService.getResource(imagePath, singleProduct.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());



    }
}
