package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.ProductClient;
import org.example.dto.PaymentRequest;
import org.example.dto.ProductDto;
import org.example.exceptions.InsufficientFundsException;
import org.example.exceptions.ProductOwnershipException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ProductClient productClient;
    public String executePayment(PaymentRequest request) {
        ProductDto product = productClient.getProductById(request.productId());

        if (!product.userId().equals(request.userId())) {
            throw new ProductOwnershipException("Продукт не принадлежит пользователю с ID: " + request.userId());
        }

        if (product.balance().compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на продукте с ID: " + request.productId());
        }
        return "Платеж выполнен успешно";
    }

}
