package com.cydeo.liveSessions.liveClass.week2;

import com.cydeo.utilities.HrTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_HamcrestHR extends HrTestBase {

    /**
     * Given accept type is Json
     * And parameters: q={"region_id":2}
     * When users sends a GET request to "/countries"
     * Then status code is 200
     * And Content type is application/json
     * And Date header is not null
     * Verify
     * - count is 5
     * - hasMore is false
     * - first country id is AR
     * - country names have Canada
     * - country names have Canada,Mexico
     * - total country size is 5
     * - each country has country_id
     * - each country region_id is 2
     * - Print country names
     */

    @DisplayName("GET Countries")
    @Test
    void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":2}")
                .when()
                .get("/countries")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .header("Date", notNullValue())
                .body("count", is(5))
                .body("hasMore", equalTo(false))
                .body("items.country_id[0]", equalTo("AR"))
                .body("items.country_name", hasItem("Canada"))
                .body("items.country_name", hasItems("Canada", "Mexico"))
                .body("items", hasSize(5))
                .body("count", is(5))
                .body("items.country_id", everyItem(notNullValue()))
                .body("items.region_id", everyItem(equalTo(2))).extract().response();


        JsonPath jsonPath = response.jsonPath();

        // RESPONSE
        List<String> allCountriesResponse = response.path("items.country_name");
        System.out.println(allCountriesResponse);

        // JSONPATH
        List<String> allCountries = jsonPath.getList("items.country_name");
        System.out.println(allCountries);

        assertEquals(5, allCountries.size());


    }
}
