package com.cydeo.liveSessions.liveClass.week2;

import com.cydeo.utilities.FruitTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01_PathParam extends FruitTestBase {

    /**
     * 1- Given accept type is Json
     * 2- Path Parameters value is
     * - id —> 4
     * 3- When user sends GET request to /products/{id}
     * 4- Verify followings
     * - Status code should be 200
     * - Content Type is application/json
     * - Print response
     * - id is 4
     * - Name is "Coconut"
     * - Vendor name is "True Fruits Inc."
     */
    @DisplayName("GET a product ") // Matchers + response !!
    @Test
    void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 4)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(4))
                .body("name", equalTo("Coconut"))
                .body("vendors[0].name", equalTo("True Fruits Inc.")).extract().response();


        //alternative
        //     *     - id is 4
        int id = response.path("id");
        assertEquals(4, id);

        assertEquals(4, (Integer) response.path("id"));


        //     *     - Name is "Coconut"
        String name = response.path("name");
        assertEquals("Coconut", name);

        //     *     - Vendor name is "True Fruits Inc."
        String vName = response.path("vendors[0].name");
        System.out.println(vName);
        assertEquals("True Fruits Inc.", vName);
    }


    @Test
    public void getSingleProductJsonPath() { // using JsonPath


        Response response = given().log().uri().accept(ContentType.JSON) // send me data in JSON format
                .pathParam("id", 4).
                when().get("/products/{id}").prettyPeek();

        JsonPath jp = response.jsonPath();

        //     *     - Status code should be 200
        assertEquals(200, response.statusCode());

        //     *     - Content Type is application/json
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //     *     - id is 4
        int id = jp.getInt("id");
        assertEquals(4, id);
        assertEquals(4, jp.getInt("id"));

        //     *     - Name is "Coconut"
        assertEquals("Coconut", jp.getString("name"));

        //     *     - Vendor name is "True Fruits Inc."
        assertEquals("True Fruits Inc.", jp.getString("vendors[0].name"));

    }


    //using ready method

    @Test
    public void getSingleProductwithHamCrestPlusJsonPath() {


        JsonPath jsonPath = getResponse("/products/{id}", 4);

        assertEquals(4, jsonPath.getInt("id"));

        assertEquals("Coconut", jsonPath.getString("name"));

        assertEquals("True Fruits Inc.", jsonPath.getString("vendors[0].name"));

    }


    //custom return method !!
    public static JsonPath getResponse(String endpoint, int pathParam) {

        return given().log().uri().accept(ContentType.JSON) // send me data in JSON format
                .pathParam("id", pathParam).
                when().get(endpoint).prettyPeek().
                then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().jsonPath();

    }

}
