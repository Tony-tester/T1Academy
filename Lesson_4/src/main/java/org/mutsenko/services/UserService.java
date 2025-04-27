package org.mutsenko.services;

import lombok.RequiredArgsConstructor;
import org.mutsenko.daos.UserDao;
import org.mutsenko.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao dao;

    public void createUser(String username) {
        dao.createUser(username);
    }

    public User getUser(Long id) {
        return dao.getUser(id);
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public void deleteUser(Long id) {
        dao.deleteUser(id);
    }
}
