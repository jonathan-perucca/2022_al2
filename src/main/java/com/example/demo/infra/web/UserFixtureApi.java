package com.example.demo.infra.web;

import com.example.demo.domain.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("!prod")
@RestController
@RequestMapping("/test-fixtures/users")
public class UserFixtureApi {

    private final UserRepository userRepository;

    public UserFixtureApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsers() {
        userRepository.deleteAll();

        return ResponseEntity.noContent().build();
    }
}
