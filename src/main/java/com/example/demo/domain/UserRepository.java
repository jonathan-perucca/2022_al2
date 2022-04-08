package com.example.demo.domain;

import java.util.List;

public interface UserRepository {
    void save(User user);

    int count();

    List<User> findAll();

    void deleteAll();
}
