package com.example.productrestapi.controller;

import com.example.productrestapi.dto.ApiResponse;
import com.example.productrestapi.dto.ProductDto;
import com.example.productrestapi.entity.Product;
import com.example.productrestapi.repository.ProductRepository;
import com.example.productrestapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @PreAuthorize("hasAuthority('GET_ONE_PRODUCT')")
    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productOptional.get());
    }

    @PreAuthorize("hasAuthority('PRODUCT_ADD')")
    @PostMapping
    public ResponseEntity add(@Valid @RequestBody ProductDto productDto) {
        ApiResponse apiResponse = productService.add(productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('PRODUCT_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        ApiResponse apiResponse = productService.edit(id, productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        ApiResponse apiResponse = productService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }


}
