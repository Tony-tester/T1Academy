package org.mutsenko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mutsenko.services.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        UserService userService = context.getBean(UserService.class);
        ObjectMapper objectMapper = new ObjectMapper();

        // Create users
        userService.createUser("alice_" + UUID.randomUUID());
        userService.createUser("bob_" + UUID.randomUUID());

        // Get all users
        System.out.println("All users:");
        userService.getAllUsers().forEach(user -> {
            try {
                String json = objectMapper.writeValueAsString(user);
                System.out.println(json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        // Get one user
        System.out.println("Get user ID 1: " + userService.getUser(1L));

        // Delete user
        userService.deleteUser(1L);
        System.out.println("After deletion:");
        userService.getAllUsers().forEach(user -> {
            try {
                String json = objectMapper.writeValueAsString(user);
                System.out.println(json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
