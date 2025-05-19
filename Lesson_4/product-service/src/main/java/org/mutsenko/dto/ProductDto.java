package org.mutsenko.dto;

import java.math.BigDecimal;

public record ProductDto(Long id, String accountNumber, BigDecimal balance, String type, Long userId) {}

