package org.example.dto;

import java.time.LocalDateTime;

public record OperationResponse(LocalDateTime timestamp, String status, String message) {}
