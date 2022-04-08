package com.example.demo.infra.web.response;

import org.springframework.hateoas.RepresentationModel;

public class UserResource extends RepresentationModel<UserResource> {
    private String name;

    public String getName() {
        return name;
    }

    public UserResource setName(String name) {
        this.name = name;
        return this;
    }
}
