package com.cydeo.day06;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01_HRDeserialization extends HrTestBase {
    /**
     * Create a test called getLocation
     * 1. Send request to GET /locations
     * 2. log uri to see
     * 3. Get all Json as a map and print out screen all the things with the help of  map
     * System.out.println("====== GET FIRST LOCATION  ======");
     * System.out.println("====== GET FIRST LOCATION LINKS  ======");
     * System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
     * System.out.println("====== FIRST LOCATION ======");
     * System.out.println("====== FIRST LOCATION ID ======");
     * System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
     * System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
     */

    @DisplayName("GET /locations to deserialization into Java Collections")
    @Test
    public void test1() {
        Response response = given()
                .log().uri()
                .accept(ContentType.JSON)
                .when()
                .get("/locations")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        System.out.println("====== GET FIRST LOCATION  ======");

        Map<String, Object> firstMap = jsonPath.getMap("items[0]");
        System.out.println("firstMap = " + firstMap);

        System.out.println("firstMap.get(\"location_id\") = " + firstMap.get("location_id"));

        System.out.println("====== GET FIRST LOCATION LINKS  ======");
        Map<String, Object> firstMapLinks = jsonPath.getMap("items[0].links[0]");
        System.out.println("firstMapLinks = " + firstMapLinks);

        System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");

        //response.as("items[0]",Map.class) --> there is no method like this, that is why as() method is NOT enough !!
        List<Map<String, Object>> allLocationsMap = jsonPath.getList("items");
        //System.out.println("allLocationsMap = " + allLocationsMap);
        for (Map<String, Object> eachLocation : allLocationsMap) {
            System.out.println("eachLocation = " + eachLocation);
        }
        //     * System.out.println("====== FIRST LOCATION ======");
        System.out.println("allLocationsMap.get(0) = " + allLocationsMap.get(0));

        //     * System.out.println("====== FIRST LOCATION ID ======");
        System.out.println("allLocationsMap.get(0).get(\"location_id\") = " + allLocationsMap.get(0).get("location_id"));

        //     * System.out.println("====== FIRST LOCATION COUNTRY_ID ======");\
        System.out.println("allLocationsMap.get(0).get(\"country_id\") = " + allLocationsMap.get(0).get("country_id"));
        //     * System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
        System.out.println("allLocationsMap.get(0).get(\"links\") = " + allLocationsMap.get(0).get("links"));

        //we want to get href from first location what we need to do ?
        List<Map<String, Object>> links = (List<Map<String, Object>>) allLocationsMap.get(0).get("links");
        System.out.println("links = " + links);
        System.out.println("links.get(0).get(\"href\") = " + links.get(0).get("href"));
    }
}


