package org.mutsenko.services;

import lombok.RequiredArgsConstructor;
import org.mutsenko.dto.ProductDto;
import org.mutsenko.entity.Product;
import org.mutsenko.entity.User;
import org.mutsenko.exceptions.ProductNotFoundException;
import org.mutsenko.repositories.ProductRepository;
import org.mutsenko.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Product addProduct(Long userId, String accountNumber, BigDecimal balance, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = new Product();
        product.setAccountNumber(accountNumber);
        product.setBalance(balance);
        product.setType(type);
        product.setUser(user);

        return productRepository.save(product);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return new ProductDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getType(),
                product.getUser().getId()
        );
    }

    public List<ProductDto> getProductsByUserId(Long userId) {
        List<ProductDto> dtos = new ArrayList<>();
        productRepository.findAllByUserId(userId).forEach(product -> {
            dtos.add(new ProductDto(
                    product.getId(),
                    product.getAccountNumber(),
                    product.getBalance(),
                    product.getType(),
                    product.getUser().getId()
            ));
        });
        return dtos;
    }
}


