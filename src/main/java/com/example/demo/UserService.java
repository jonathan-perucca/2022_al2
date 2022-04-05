package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UserService {
    private final UserStore userStore;
    private final UserConfig userConfig;

    public UserService(UserStore userStore, UserConfig userConfig) {
        this.userStore = userStore;
        this.userConfig = userConfig;
    }

    public User add(String username) {
        if (userStore.count() + 1 > userConfig.getMaxUsers()) {
            throw new IllegalStateException("Cannot add more users");
        }

        var user = new User().setId(UUID.randomUUID().toString()).setName(username);
        userStore.save(user);

        return user;
    }

    public List<User> getAll() {
        return userStore.findAll();
    }
}

class User {
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

@Component
class UserStore {
    private final Map<String, User> db;

    UserStore(Map<String, User> db) {
        this.db = db;
    }

    public void save(User user) {
        db.put(user.getId(), user);
    }

    public int count() {
        return db.size();
    }

    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }
}

@Component
class UserConfig {
    private int maxUsers;

    public UserConfig(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getMaxUsers() {
        return maxUsers;
    }
}