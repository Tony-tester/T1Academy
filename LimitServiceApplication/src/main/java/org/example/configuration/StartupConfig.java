package org.example.configuration;

import org.example.service.LimitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Bean
    public CommandLineRunner seedData(LimitService limitService) {
        return args -> limitService.seedInitialLimits();
    }
}
