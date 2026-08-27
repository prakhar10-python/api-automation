package com.example.api.tests;

import com.example.api.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PostsApiTest extends BaseTest {

    @Test(description = "GET /posts/{id} returns the expected post")
    public void getPostById() {
        given()
                .spec(requestSpec)
        .when()
                .get("/posts/{id}", 1)
        .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1));
    }

    @Test(description = "GET /posts returns a non-empty list")
    public void getAllPosts() {
        given()
                .spec(requestSpec)
        .when()
                .get("/posts")
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(description = "POST /posts creates a post")
    public void createPost() {
        Map<String, Object> payload = Map.of(
                "title", "foo",
                "body", "bar",
                "userId", 1
        );

        given()
                .spec(requestSpec)
                .body(payload)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("title", equalTo("foo"))
                .body("userId", equalTo(1));
    }
}
