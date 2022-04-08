package com.example.demo.infra;

import com.example.demo.fixture.UserFixtures;
import com.example.demo.infra.web.response.UserResource;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        UserFixtures.cleanUsers();
    }

    @Test
    void should_retrieve_bootstrapped_users() {
        UserFixtures.createUser("axel");

        var userResources = when()
                .get("/api/users")
        .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getObject(".", new TypeRef<List<UserResource>>() {});

        assertThat(userResources).hasSize(1);
    }

    @Test
    void should_create_user() {
        var location = UserFixtures.createUser("axe")
        .then()
                .statusCode(201)
                .extract().header("Location");

        assertThat(location).isNotEmpty();

        var userResource = when()
                .get(location)
        .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResource.class);

        assertThat(userResource.getName()).isEqualTo("axe");
        assertThat(userResource.getRequiredLink("self").getHref()).isNotEmpty();
    }

}