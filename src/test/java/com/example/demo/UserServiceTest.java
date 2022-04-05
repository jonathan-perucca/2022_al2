package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    UserService userService;
    UserStore userStore;
    UserConfig userConfig;

    @BeforeEach
    void setup() {
        userStore = new UserStore(new HashMap<>());
        userConfig = new UserConfig(2);

        userService = new UserService(userStore, userConfig);
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