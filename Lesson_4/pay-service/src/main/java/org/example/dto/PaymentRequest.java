package org.example.dto;

import java.math.BigDecimal;

public record PaymentRequest(Long userId, Long productId, BigDecimal amount) {}

