package org.example.dto;

import java.math.BigDecimal;

public record LimitRequest(Long userId, BigDecimal amount) {}

