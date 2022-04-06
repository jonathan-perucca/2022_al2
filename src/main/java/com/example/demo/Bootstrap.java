package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public Bootstrap(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    void onStartup(ApplicationReadyEvent event) {
        log.info("Reacting on application ready event");

        List.of("axel").forEach(username -> {
                userService.add(username);
                log.info("{} user added", username);
                eventPublisher.publishEvent( new UserAdded(username) );
        });
    }
}

@Component
class OtherComponent {
    private static final Logger log = LoggerFactory.getLogger(OtherComponent.class);

    @EventListener
    void on(UserAdded event) {
        log.info("reacting on {} added", event.getUsername());
    }
}

class UserAdded {
    private String username;

    public UserAdded(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}