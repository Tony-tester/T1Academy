package org.mutsenko.controllers;

import lombok.RequiredArgsConstructor;
import org.mutsenko.dto.ProductDto;
import org.mutsenko.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/by-user/{userId}")
    public List<ProductDto> getProductsByUser(@PathVariable Long userId) {
        return productService.getProductsByUserId(userId);
    }
}

