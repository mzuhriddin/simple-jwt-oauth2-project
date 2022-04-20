package com.example.productrestapi.service;

import com.example.productrestapi.dto.ApiResponse;
import com.example.productrestapi.dto.ProductDto;
import com.example.productrestapi.entity.Product;
import com.example.productrestapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record ProductService(ProductRepository productRepository) {
    public ApiResponse add(ProductDto productDto) {
        productRepository.save(Product.builder()
                        .name(productDto.getName())
                        .price(productDto.getPrice())
                .build());
        return ApiResponse.builder().message("ADDED").success(true).build();
    }

    public ApiResponse edit(Integer id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setPrice(productDto.getPrice());
            product.setName(productDto.getName());
            productRepository.save(product);
            return ApiResponse.builder().message("EDITED").success(true).build();
        }
        return ApiResponse.builder().message("PRODUCT NOT FOUND").success(false).build();
    }

    public ApiResponse delete(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return ApiResponse.builder().message("DELETED").success(true).build();
        }
        return ApiResponse.builder().success(false).message("PRODUCT NOT FOUND").build();
    }
}
