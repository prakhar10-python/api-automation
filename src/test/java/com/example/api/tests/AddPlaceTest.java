package com.example.api.tests;

import com.example.api.base.CourseBaseTest;
import com.example.api.config.ConfigReader;
import com.example.api.endpoints.PlaceApiEndPoints;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class AddPlaceTest extends CourseBaseTest {
    //Validate add place Api

    @Test
    public void addPlace() {
        given().
                log().all()
                .queryParam("key", ConfigReader.getCourseKey())
                .header("Content-Type", ConfigReader.getContentType())
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Frontline house\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}")
                .when().post(PlaceApiEndPoints.PLACE)
                .then().assertThat().statusCode(200)
                .body("scope",equalTo("APP"))
                .log().all();

    }

}