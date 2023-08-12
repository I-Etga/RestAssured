package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//thanks to that two import statement below, we don't have to write Assertion. & RestAssured. every single time !!
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P02_NegativeSpartanTests {


    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://54.173.124.216:8000";
    }

    public void getAllSpartans() {

        Response response = given().accept(ContentType.JSON) // hey api please send me json response
                .when()
                .get("/api/spartans");
    }

    /*
     *Given Accept type application/xml
     * When User send GET request to /api/spartans/10 end point
     * Then status code must be 406
     * And response: Content Type must be application/xml;charset=UTF-8

     */

    @DisplayName("GET-All Spartans -Accept, application/xml - 406")
    @Test
    public void xmlTest() {
        Response response = given().accept(ContentType.XML).
                when().get("/api/spartans/10");

        //Then status code must be 406
        assertEquals(406, response.statusCode());

        //And response: Content Type must be application/xml;charset=UTF-8
        assertEquals("application/xml;charset=UTF-8", response.contentType());


    }
}
