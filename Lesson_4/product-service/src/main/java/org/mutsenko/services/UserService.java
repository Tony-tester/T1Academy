package org.mutsenko.services;

import lombok.RequiredArgsConstructor;
import org.mutsenko.entity.User;
import org.mutsenko.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void createUser(String username) {
        repository.save(new User(null, username, null));
    }

    public User getUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
