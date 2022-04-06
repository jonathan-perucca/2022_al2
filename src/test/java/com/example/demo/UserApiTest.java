package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class UserApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void should_retrieve_bootstrapped_users() {
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
        var request = new CreateUserRequest();
        request.setUsername("axe");
        var location = given()
                .contentType(ContentType.JSON)
                .body(request)
       .when()
                .post("/api/users")
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