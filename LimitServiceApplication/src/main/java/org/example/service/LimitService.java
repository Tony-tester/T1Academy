package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Limit;
import org.example.exceptions.LimitException;
import org.example.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class LimitService {

    private final LimitRepository repository;
    @Value("${limit.default-value}")
    private BigDecimal defaultLimit;

    public BigDecimal getRemainingLimit(Long userId) {
        Limit limit = repository.findByUserId(userId)
                .orElseGet(() -> repository.save(new Limit(userId, defaultLimit, defaultLimit)));
        return limit.getRemaining();
    }

    public void decreaseLimit(Long userId, BigDecimal amount) {
        Limit limit = repository.findByUserId(userId)
                .orElseThrow(() -> new LimitException("User not found"));

        if (limit.getRemaining().compareTo(amount) < 0) {
            throw new LimitException("Insufficient daily limit");
        }
        limit.setRemaining(limit.getRemaining().subtract(amount));
        repository.save(limit);
    }

    public void restoreLimit(Long userId, BigDecimal amount) {
        Limit limit = repository.findByUserId(userId)
                .orElseThrow(() -> new LimitException("User not found"));
        limit.setRemaining(limit.getRemaining().add(amount));
        repository.save(limit);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyLimits() {
        repository.resetAllRemainingLimits();
    }

    public void seedInitialLimits() {
        LongStream.rangeClosed(1, 100).forEach(userId -> {
            repository.findByUserId(userId).orElseGet(() -> {
                return repository.save(new Limit(userId, defaultLimit, defaultLimit));
            });
        });
    }
}

