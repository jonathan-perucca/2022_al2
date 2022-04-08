package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final UserConfig userConfig;

    public UserService(UserRepository userRepository, UserConfig userConfig) {
        this.userRepository = userRepository;
        this.userConfig = userConfig;
    }

    public User add(String username) {
        if (userRepository.count() + 1 > userConfig.getMaxUsers()) {
            throw new IllegalStateException("Cannot add more users");
        }

        var user = new User().setId(UUID.randomUUID().toString()).setName(username);
        userRepository.save(user);

        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}

@Entity
class User {
    @Id
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}

@ConfigurationProperties(prefix = "app")
class UserConfig {
    private int maxUsers;

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }
}