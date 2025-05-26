package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "user_limit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private BigDecimal dailyLimit;

    private BigDecimal remaining;

    public Limit(Long userId, BigDecimal dailyLimit, BigDecimal remaining) {
        this.userId = userId;
        this.dailyLimit = dailyLimit;
        this.remaining = remaining;
    }
}
