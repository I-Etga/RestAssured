package com.cydeo.day04;

import com.cydeo.utilities.CydeoTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_CydeoTrainingAPITests extends CydeoTestBase {

 /*
    Given accept type is application/json
    And path param is 2
    When user send request /student/:id
    Then status code should be 200
    And content type is application/json;charset=UTF-8
    And Date header is exist
    And Server header is envoy
    And verify following
                firstName Mark
                batch 13
                major math
                emailAddress mark@email.com
                companyName Cydeo
                street 777 5th Ave
                zipCode 33222
    */

    @DisplayName("GET students/2")
    @Test
    void test1() {

        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParams("id", 2)
                .when().get("/student/{id}");

        response.prettyPrint();

        // Then status code should be 200
        assertEquals(200, response.statusCode());

        //    And content type is application/json;charset=UTF-8
        assertEquals("application/json;charset=UTF-8", response.contentType());

        //    And Date header exists
        assertTrue(response.headers().hasHeaderWithName("date"));

        //    And server header is envoy
        assertEquals("envoy", response.header("server").toString());

        //create jsonPath object
        JsonPath jsonPath = response.jsonPath();

        //    And verify following
        //                firstName Mark
        assertEquals("Mark", jsonPath.getString("students[0].firstName"));

        //                batch 13
        assertEquals(13, jsonPath.getInt("students[0].batch"));

        //                major math
        assertEquals("math", jsonPath.getString("students[0].major"));

        //                emailAddress mark@email.com
        assertEquals("mark@email.com", jsonPath.getString("students[0].contact.emailAddress"));

        //                companyName Cydeo
        assertEquals("Cydeo", jsonPath.getString("students[0].company.companyName"));

        //                street 777 5th Ave
        assertEquals("777 5th Ave", jsonPath.getString("students[0].company.address.street"));

        //                zipCode 33222
        assertEquals(33222, jsonPath.getInt("students[0].company.address.zipCode"));


    }
}
