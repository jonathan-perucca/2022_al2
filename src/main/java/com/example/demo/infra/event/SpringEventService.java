package com.example.demo.infra.event;

import com.example.demo.domain.event.EventService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventService implements EventService {

    private final ApplicationEventPublisher eventPublisher;

    public SpringEventService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public <T> void publish(T event) {
        eventPublisher.publishEvent(event);
    }
}