package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
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


public class P04_DeserializationToCollections extends SpartanTestBase {
/*
     Given accept type is application/json
     And Path param id = 10
     When I send GET request to /api/spartans
     Then status code is 200
     And content type is json
     And spartan data matching:
         id > 10
         name>Lorenza
         gender >Female
         phone >3312820936
     */


    @Test
    void test1() {

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 10)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

        //If you want to convert your JSON to Java collections(map,list,custom class)..
        //this is called Deserialization
        //JSON --> JAVA
        //we need either Jackson or Gson
        //which one we added to pom.xml ?
        //Jackson

        //Solution 1 -> using response as method
        Map<String, Object> spartanMap = response.as(Map.class);

        System.out.println("spartanMap = " + spartanMap);

        int id = (int) spartanMap.get("id");
        String name = (String) spartanMap.get("name");

        System.out.println("id = " + id);
        System.out.println("name = " + name);

        //Solution 2 -> JsonPath
        JsonPath jsonPath = response.jsonPath();

        Map<String, Object> jsonPathMap = jsonPath.getMap("");
        System.out.println("jsonPathMap = " + jsonPathMap);

        int idJson = (int) jsonPathMap.get("id");
        System.out.println("idJson = " + idJson);
    }


    @DisplayName("GET All Spartans with Java Collections")
    @Test
    public void test2(){

        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/spartans/")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

        //Approach #1 -->With response object.

        List<Map<String,Object>> spartanList = response.as(List.class);

//        for (Map<String, Object> map : spartanList) {
//            System.out.println("spartanMap = " + map);
//        }

        //how to find first spartan info
        System.out.println("spartanList.get(0) = " + spartanList.get(0));

        //how to find first spartan name
        System.out.println("spartanList.get(0).get(\"name\") = " + spartanList.get(0).get("name"));

        //how to find first spartan id
        System.out.println("spartanList.get(0).get(\"id\") = " + spartanList.get(0).get("id"));



        //Approach #2 -- JSONPATH
        JsonPath jsonPath = response.jsonPath();
        List<Map<String,Object>> listSpartan = jsonPath.getList("");

        System.out.println("listSpartan = " + listSpartan);

        //how to find first spartan info
        System.out.println("spartanList.get(0) = " + listSpartan.get(0));

        //how to find first spartan name
        System.out.println("spartanList.get(0).get(\"name\") = " + listSpartan.get(0).get("name"));

        //how to find first spartan id
        System.out.println("spartanList.get(0).get(\"id\") = " + listSpartan.get(0).get("id"));


    }


}
