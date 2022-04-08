package com.example.demo.domain.event;

public class UserAdded {
    private String username;

    public UserAdded(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
