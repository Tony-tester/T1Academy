package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.LimitRequest;
import org.example.dto.LimitResponse;
import org.example.dto.OperationResponse;
import org.example.service.LimitService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/limits")
@RequiredArgsConstructor
public class LimitController {

    private final LimitService service;

    @GetMapping("/{userId}")
    public LimitResponse getLimit(@PathVariable Long userId) {
        BigDecimal limit = service.getRemainingLimit(userId);
        return new LimitResponse(userId, limit);
    }

    @PostMapping("/decrease")
    public OperationResponse decrease(@RequestBody LimitRequest request) {
        service.decreaseLimit(request.userId(), request.amount());
        return new OperationResponse(LocalDateTime.now(), "success", "Limit decreased successfully");
    }

    @PostMapping("/restore")
    public OperationResponse restore(@RequestBody LimitRequest request) {
        service.restoreLimit(request.userId(), request.amount());
        return new OperationResponse(LocalDateTime.now(), "success", "Limit restored successfully");
    }
}
