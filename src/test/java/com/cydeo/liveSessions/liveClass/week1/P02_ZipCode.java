package com.cydeo.liveSessions.liveClass.week1;

import com.cydeo.utilities.ZipCodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P02_ZipCode extends ZipCodeTestBase {

    /**
     * TASK 1
     * Given Accept application/json
     * And path zipcode is 22031
     * When I send a GET request to /us/{zipcode} endpoint
     * Then status code must be 200
     * And content type must be application/json
     * And Server header is cloudflare
     * And Report-To header exists
     * And body should contain following information
     * post code is 22031
     * country is United States
     * country abbreviation is US
     * place name is Fairfax
     * state is Virginia
     */

    @Test
    void test1() {

        given().log().uri()
                .accept(ContentType.JSON)
                .pathParam("zipCode", 22031).
                when().get("/us/{zipCode}").prettyPeek().
                then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Server", "cloudflare")
                .header("Report-To", notNullValue())
                .body("'post code'", is("22031"))
                .body("country", is("United States"))
                .body("'country abbreviation'", is("US"))
                .body("places[0].'place name'", is("Fairfax"))
                .body("places[0].state", is("Virginia"));
    }

    @Test
    public void task1JsonPath() {

        JsonPath jp = given().log().uri()
                .accept(ContentType.JSON)
                .pathParam("zipCode", 22031).
                when().get("/us/{zipCode}").
                then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Server", "cloudflare")
                .header("Report-To", notNullValue())
                .extract().jsonPath();

        // GET JSON PATH TO START ASSERTIONS


        //     * 'post code' is 22031
        assertEquals("22031", jp.getString("'post code'"));

        //     * country is United States
        assertEquals("United States", jp.getString("country"));

        //     * country abbreviation is US
        assertEquals("US", jp.getString("'country abbreviation'"));

        //     * 'place name' is Fairfax
        assertEquals("Fairfax", jp.getString("places[0].'place name'"));

        //     * state is Virginia
        assertEquals("Virginia", jp.getString("places[0].state"));


    }

    /**
     * TASK 2
     * Given Accept application/json
     * And path zipcode is 50000
     * When I send a GET request to /us endpoint
     * Then status code must be 404
     * And content type must be application/json
     */
    @Test
    public void task2() {

        given().log().uri()
                .accept(ContentType.JSON)
                .pathParam("zipCode", 50000).
                when().get("/us/{zipCode}").
                then()
                .statusCode(404)
                .contentType(ContentType.JSON);

        // Which kind of test case we created into here
        // negative test


    }

    /**
     * Given Accept application/json
     * And path state is va
     * And path city is fairfax
     * When I send a GET request to /us endpoint
     * Then status code must be 200
     * And content type must be application/json
     * And payload should contains following information
     * country abbreviation is US
     * country United States
     * place name Fairfax
     * each places must contains fairfax as a value
     * each post code must start with 22
     */

    @Test
    public void task3() {

        JsonPath jp = given().log().uri()
                .accept(ContentType.JSON)
                .pathParam("state", "va")
                .pathParam("city", "fairfax").
                when().get("/us/{state}/{city}").
                then().statusCode(200)
                .contentType(ContentType.JSON)
                .extract().jsonPath();


        //     * country abbreviation is US
        assertEquals("US", jp.getString("'country abbreviation'"));

        //     * country United States
        assertEquals("United States", jp.getString("country"));

        //     * place name Fairfax
        assertEquals("Fairfax", jp.getString("'place name'"));


        //     * each places must contains Fairfax as a value
        List<String> allPlaces = jp.getList("places.'place name'");
        System.out.println(allPlaces);

        // how to do verification
        for (String eachPlace : allPlaces) {
            assertTrue(eachPlace.contains("Fairfax"));
        }

        //     * each post code must start with 22
        List<String> allPostCodes = jp.getList("places.'post code'");
        System.out.println(allPostCodes);

        // how to do verification
        for (String eachPostCode : allPostCodes) {
            assertTrue(eachPostCode.startsWith("22"));
        }

    }
}
