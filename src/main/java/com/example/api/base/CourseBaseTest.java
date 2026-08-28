package com.example.api.base;

import com.example.api.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class CourseBaseTest {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void courseSetup() {
        RestAssured.baseURI = ConfigReader.getCourseBaseTest();
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

}
