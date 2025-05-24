package org.example.dto;

import java.math.BigDecimal;

public record LimitResponse(Long userId, BigDecimal remaining) {}
