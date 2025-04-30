package org.mutsenko;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mutsenko.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Добавим пару пользователей
        userService.createUser("alice_" + UUID.randomUUID());
        userService.createUser("bob_" + UUID.randomUUID());

        // Выведем всех
        userService.getAllUsers().forEach(user -> {
            try {
                System.out.println(objectMapper.writeValueAsString(user));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
