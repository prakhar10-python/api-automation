package com.example.api.tests;

import com.example.api.base.CourseBaseTest;
import com.example.api.config.ConfigReader;
import com.example.api.endpoints.PlaceApiEndPoints;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class AddPlaceTest extends CourseBaseTest {

    String placeId;
    String newAddress = "Daffodils PG,123";

    //Validate add place Api
    // JSON Path is for the response parsing
    @Test(priority = 1)
    public void addPlace() {
        placeId = given().
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
                .when().post(PlaceApiEndPoints.ADD_PLACE)
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .log().all()
                .extract().response().jsonPath().getString("place_id");
    }


    //Validate update place
    @Test(dependsOnMethods = "addPlace")
    public void updatePlace() {
       given()
                .queryParam("key", ConfigReader.getCourseKey())
                .header("Content-Type", ConfigReader.getContentType())
                .body("{\n" +
                        "  \"place_id\": \"" + placeId + "\",\n" +
                        "  \"address\": \"" + newAddress + "\",\n" +
                        "  \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put(PlaceApiEndPoints.UPDATE_PLACE)
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }

    @Test(dependsOnMethods = "updatePlace")
    public void getPlaceAfterUpdation() {
        Response response = given().log().all()
                .queryParam("key", ConfigReader.getCourseKey())
                .queryParam("place_id", placeId)
                .when().get(PlaceApiEndPoints.GET_PLACE)
                .then().assertThat().statusCode(200)
                .body("address", equalTo(newAddress)) //given when then asserstion
                .log().all()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        String actualAddress = jsonPath.get("address");
        Assert.assertEquals(newAddress,actualAddress); //assertion using java assertion library from testng

    }

    @Test(dependsOnMethods = "getPlaceAfterUpdation")
    public void deletePlace() {
        given()
                .log().all()
                .queryParam("key", ConfigReader.getCourseKey())
                .queryParam("place_id", placeId)
                .when().delete(PlaceApiEndPoints.DELETE_PLACE)
                .then().assertThat().statusCode(200)
                .log().all();

    }
}