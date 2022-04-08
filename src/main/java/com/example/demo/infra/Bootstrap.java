package com.example.demo.infra;

import com.example.demo.domain.event.UserAdded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @EventListener
    void onStartup(UserAdded event) {
        log.info("Reacting on user added for username {}", event.getUsername());
    }
}
