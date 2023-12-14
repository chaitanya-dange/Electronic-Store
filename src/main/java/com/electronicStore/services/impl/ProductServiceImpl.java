package com.electronicStore.services.impl;

import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.dtos.ProductDto;
import com.electronicStore.entities.Product;
import com.electronicStore.exceptions.ResourceNotFoundException;
import com.electronicStore.helpers.Helper;
import com.electronicStore.repository.ProductRepository;
import com.electronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto create(ProductDto productDto) {

        Product product = modelMapper.map(productDto, Product.class);
        product.setProductId( UUID.randomUUID().toString().toString());
       product.setAddedDate(new Date());
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product doesn't found"));

        product.setDescription(productDto.getDescription());
        product.setLive(productDto.isLive());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.isStock());
        product.setQuantity(productDto.getQuantity());
        product.setTitle(productDto.getTitle());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setProductImageName(productDto.getProductImageName());
//        product.setAddedDate(productDto.getAddedDate());

        productRepository.save(product);

        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        productRepository.deleteById(productId);

    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize , String sortBy, String sortDir ) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?( Sort.by(sortBy).descending()):( Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> allPageProducts = productRepository.findAll(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allPageProducts, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize , String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?( Sort.by(sortBy).descending()):( Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pageProductList = productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(pageProductList, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize , String sortBy, String sortDir) {


        Sort sort=sortDir.equalsIgnoreCase("desc")?( Sort.by(sortBy).descending()):( Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pageProductList = productRepository.findByTitleContaining(pageable,subTitle);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(pageProductList, ProductDto.class);
        return pageableResponse;
    }
}
