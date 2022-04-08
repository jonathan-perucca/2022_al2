package com.example.demo.fixture;

import com.example.demo.infra.web.request.CreateUserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class UserFixtures {

    public static void cleanUsers() {
        when().delete("/test-fixtures/users").then().statusCode(204);
    }

    public static Response createUser(String name) {
        var request = new CreateUserRequest();
        request.setUsername(name);
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/users");
    }
}
