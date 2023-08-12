package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01_HrWithParameters extends HrTestBase {

    /*
     * Given accept type is json
     * And parameters: g = "{"region_id":2}"
     * When users sends a GET request to "/countries"
     * Then status code is 200
     * And Content type is application/json
     * And Payload should contain "United States of America"
     */
    @DisplayName("GET Request /countries with 'region_id' ")
    @Test
    void test1() {

        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("g", "{\"region_id\":2}")
                .when()
                .get("/countries");

        //print response
        response.prettyPrint();

        // Then status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        // And Content type is application/json
        assertEquals("application/json", response.contentType());

        //And Payload should contain "United States of America"
        assertTrue(response.body().asString().contains("United States of America"));


    }
}
