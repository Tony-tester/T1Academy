package org.example.repository;

import org.example.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {
    Optional<Limit> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Limit l SET l.remaining = l.dailyLimit")
    void resetAllRemainingLimits();

}

