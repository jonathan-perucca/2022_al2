package com.example.demo.domain;

import com.example.demo.domain.event.EventService;
import com.example.demo.domain.event.UserAdded;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final UserConfig userConfig;
    private final EventService eventService;

    public UserService(UserRepository userRepository,
                       UserConfig userConfig,
                       EventService eventService) {
        this.userRepository = userRepository;
        this.userConfig = userConfig;
        this.eventService = eventService;
    }

    public User add(String username) {
        if (userRepository.count() + 1 > userConfig.getMaxUsers()) {
            throw new IllegalStateException("Cannot add more users");
        }

        var user = new User().setId(UUID.randomUUID().toString()).setName(username);
        userRepository.save(user);

        eventService.publish(new UserAdded(user.getName()));

        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}