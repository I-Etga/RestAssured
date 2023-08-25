package com.cydeo.day07;


import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_SpartanPUTPATCHDELETE extends SpartanTestBase {

    @DisplayName("PUT Spartan with Map")
    @Test
    void test1() {

        //we can provide json request body with map,pojo,string. All valid here too

        Map<String, Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name", "John Doe PUT");
        requestBodyMap.put("gender", "Male");
        requestBodyMap.put("phone", "8877445596");

        //PUT will update an existing record. So we choose the existing ID
        //Make sure ut exist in your database
        int id = 116;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(requestBodyMap)
                .when()
                .put("/api/spartans/{id}")
                .then()
                .statusCode(204);
    }

    @DisplayName("PATCH Spartan with Map")
    @Test
    void test2() {

        //we can provide json request body with map,pojo,string. All valid here too

        Map<String, Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name", "John Doe PATCH");
        requestBodyMap.put("gender", "Male");
        requestBodyMap.put("phone", "8877445596");

        //PUT will update an existing record. So we choose the existing ID
        //Make sure ut exist in your database
        int id = 116;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(requestBodyMap)
                .when()
                .patch("/api/spartans/{id}")
                .then()
                .statusCode(204);
    }

    @DisplayName("DELETE Spartan")
    @Test
    void test3() {

        //PUT will update an existing record. So we choose the existing ID
        //Make sure ut exist in your database
        int id = 116;

        given()
                .pathParam("id", id)
                .when()
                .delete("/api/spartans/{id}")
                .then()
                .statusCode(204);

        //after it's deleted, when we send GET request, it needs to give 404
        given()
                .pathParam("id", id)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(404);
    }
}
