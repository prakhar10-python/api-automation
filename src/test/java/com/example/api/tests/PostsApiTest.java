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
                .get("/posts/1")
        .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1));
    }
}
