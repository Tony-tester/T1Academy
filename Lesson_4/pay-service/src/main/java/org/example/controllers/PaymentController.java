package org.example.controllers;


import lombok.RequiredArgsConstructor;
import org.example.client.ProductClient;
import org.example.dto.PaymentRequest;
import org.example.dto.ProductDto;
import org.example.services.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final ProductClient productClient;
    private final PaymentService service;

    @GetMapping("/products/{userId}")
    public List<ProductDto> getUserProducts(@PathVariable Long userId) {
        return productClient.getProductsByUserId(userId);
    }

    @PostMapping("/execute")
    public Map<String, String> executePayment(@RequestBody PaymentRequest request) {
        String message = service.executePayment(request);
        return Map.of("message", message);
    }
}


