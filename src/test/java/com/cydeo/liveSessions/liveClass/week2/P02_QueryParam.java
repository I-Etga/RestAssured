package com.cydeo.liveSessions.liveClass.week2;

import com.cydeo.utilities.FruitTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P02_QueryParam extends FruitTestBase {

    /**
     * 1- Given accept type is Json
     * 2- Query Parameters value is
     * - start —> 1
     * - limit —> 100
     * - search —> "Fruit"
     * 3- When user sends GET request to /products
     * 4- Verify followings
     * - Status code should be 200
     * - Content Type is application/json
     * - start and limit values are matching with query params
     * - Product Names contains Fruit
     * - Get all product names
     * - Get product ids
     */

    @Test
    void test1() {
        JsonPath jsonPath =
                given()
                    .accept(ContentType.JSON)
                    .queryParam("start", 1)
                    .queryParam("limit", 100)
                    .queryParam("search", "Fruit")
                .when()
                    .get("/products")
                .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("meta.start", is(1))
                    .body("meta.limit", is(100))
                    .body("products.name", everyItem(containsString("Fruit")))
                .extract().jsonPath();


        List<String> productList = jsonPath.getList("products.name");

        //   assertion by using json -> alternative of line 49
        for (String each : productList) {
            assertTrue(each.contains("Fruit"));
        }

        List<String> idList = jsonPath.getList("products.id");
        System.out.println("idList = " + idList);

    }
}
