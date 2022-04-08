package com.example.demo.domain;

import com.example.demo.domain.UserRepository;
import com.example.demo.domain.UserService;
import com.example.demo.infra.config.SpringUserConfig;
import com.example.demo.infra.event.NoOpEventService;
import com.example.demo.infra.repository.memory.InMemoryUserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    UserService userService;
    UserRepository userStore;
    SpringUserConfig userConfig;

    @BeforeEach
    void setup() {
        userStore = new InMemoryUserStore(new HashMap<>());
        userConfig = new SpringUserConfig();
        userConfig.setMaxUsers(2);

        userService = new UserService(userStore, userConfig, new NoOpEventService());
    }

    @Test
    void should_retrieve_empty_user_list_when_no_user_added() {
        var result = userService.getAll();

        assertThat(result).hasSize(0);
    }

    @Test
    void should_add_user_in_store() {
        userService.add("testy");

        var users = userService.getAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("testy");
    }

    @Test
    void should_generate_error_when_max_users_reached() {
        userService.add("testy");
        userService.add("testo");

        assertThatThrownBy(() -> userService.add("te"))
                .isInstanceOf(IllegalStateException.class);
//        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
//            @Override
//            public void call() throws Throwable {
//                userService.add("te");
//            }
//        });
    }
}