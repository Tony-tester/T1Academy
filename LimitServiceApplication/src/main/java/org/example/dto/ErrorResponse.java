package org.example.dto;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, String error, String message) {}
