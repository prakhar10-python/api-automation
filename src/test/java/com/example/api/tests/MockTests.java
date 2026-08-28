package com.example.api.tests;

import com.example.api.base.CourseBaseTest;
import com.example.api.config.ConfigReader;
import com.example.api.endpoints.mockdata;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MockTests {

    JsonPath js = new JsonPath(mockdata.mockData());

    public String FIRST_COURSE_TITLE = "Selenium Python";



    @Test
    public void coursesLength() {
        int coursesLength = js.getList("courses").size();
        Assert.assertEquals(coursesLength, 3);
    }

    @Test
    public void purchaseAmount() {
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(purchaseAmount, 910,"Purchase Amount is not matching");
    }

    @Test
    public void firstCourseTitle() {
        String firstCourseTitle = js.getString("courses[0].title");
        Assert.assertEquals(firstCourseTitle, FIRST_COURSE_TITLE);
    }

    @Test
    public void allCourseTitleAndPrices() {
        String course1Title = js.getString("courses[0].title");
        String course1Price = js.getString("courses[0].price");

        String course2Title = js.getString("courses[1].title");
        String course2Price = js.getString("courses[1].price");

        String course3Title = js.getString("courses[2].title");
        String course3Price = js.getString("courses[2].price");

        System.out.println(course1Title +" : " +course1Price);
        System.out.println(course2Title +" : " +course2Price);
        System.out.println(course3Title +" : " +course3Price);
    }

    @Test
    public void rpaCourseTitle() {
        int numberOfCourses = js.getList("courses").size();
        int copies=0;
        for (int i=0;i<numberOfCourses;i++) {
            String courseTitle = js.getString("courses["+i+"].title");
            if (courseTitle.equals("RPA")) {
                copies = js.getInt("courses["+i+"].copies");
                break;
            }
        }
        System.out.println(copies);
    }

    @Test
    public void allCoursePrice() {
        int numberOfCourses = js.getList("courses").size();
        int totalPrice = 0;
        int coursePrice;
        int courseCopies;
        for (int i=0;i<numberOfCourses;i++) {
            courseCopies = js.getInt("courses["+i+"].copies");
            coursePrice = js.getInt("courses["+i+"].price");
            totalPrice +=(coursePrice * courseCopies);
        }

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(totalPrice, purchaseAmount,"Purchase Amount calculated is not expected");
    }
}
