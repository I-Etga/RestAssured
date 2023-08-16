package com.cydeo.day04;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class P02_HrWithJsonPath extends HrTestBase {

    @DisplayName("GET all /countries")
    @Test
    public void Test() {
        Response response = get("/countries");

        response.prettyPrint();

        //verify status code
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //create jsonpath object
        JsonPath jsonPath = response.jsonPath();

        //get me 3rd country name
        System.out.println("jsonPath.getString(\"items[2].country_name\") = " + jsonPath.getString("items[2].country_name"));

        //get me 3rd and 4th country name
        jsonPath.getString("items[2].country_name");
        jsonPath.getString("items[3].country_name");

        //alternative and short way !! items[2,3]
        System.out.println("jsonPath.getString(\"items[2,3].country_name\") = " + jsonPath.getString("items[2,3].country_name"));

        //get me all country name where region_id is 2 [SYNTAX is really important ]
        List<String> list = jsonPath.getList("items.findAll {it.region_id ==2}.country_name");
        System.out.println("list = " + list);
    }

    @DisplayName("GET all /employees?limit=200 with JsonPath")
    @Test
    void test2() {

        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("limit", 200)
                .when().get("/employees");

        //response.prettyPrint();

        //assert status code
        assertEquals(200, response.statusCode());

        //create jsonpath objecy
        JsonPath jsonPath = response.jsonPath();

        //get all emails from response
        List<String> allEmails = jsonPath.getList("items.email");
        System.out.println("allEmails = " + allEmails);
        System.out.println("allEmails.size() = " + allEmails.size());

        //jsonPath METHODS [findAll , min ,max ...] !!

        //get all emails who are working as IT_PROG
        List<String> listIT_PROG = jsonPath.getList("items.findAll {it.job_id == 'IT_PROG'}.email");
        //List<String> listIT_PROG = jsonPath.getList("items.findAll {it.job_id.equals(\"IT_PROG\")}.email");
        System.out.println("listIT_PROG = " + listIT_PROG);

        //get me all employees first names whose salary is more than 10000
        List<String> listSalary10kPlus = jsonPath.getList("items.findAll {it.salary > 1000}.first_name");
        System.out.println("listSalary10kPlus = " + listSalary10kPlus);

        //get me all info from response who has max salary
        System.out.println("jsonPath.getString(\"items.max {it.salary}\") = " + jsonPath.getString("items.max {it.salary}"));

        //get me first name from response who has max salary
        System.out.println("jsonPath.getString(\"items.max {it.salary}.first_name\") = " + jsonPath.getString("items.max {it.salary}.first_name"));

        // get me first name from response who has min salary
        System.out.println("jsonPath.getString(\"items.min {it.salary}.first_name\") = " + jsonPath.getString("items.min {it.salary}.first_name"));


    }

    /*
    TASK
Given
accept type is application/json
When
user sends get request to /locations
Then
response status code must be 200
content type equals to application/json
get the second city with JsonPath get the last city with JsonPath
get all country ids
get all city where their country id is UK

     */

    @Test
    void test3() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/locations");

        response.prettyPrint();
        //response status code must be 200
        assertEquals(200, response.statusCode());

        //content type equals to application/json
        assertEquals("application/json", response.contentType());

        //get the second city with JsonPath get the last city with JsonPath

        //create jsonPath object Step#1
        JsonPath jsonPath = response.jsonPath();

        //Step #2.1 get the second city
        System.out.println("jsonPath.getString(\"items[1].city\") = " + jsonPath.getString("items[1].city"));

        //Step #2.2 get the last city --> index[-1 ] returns the last one !!
        System.out.println("jsonPath.getString(\"items[-1].city\") = " + jsonPath.getString("items[-1].city"));

        //jsonPath methods [findAll , min ,max ...]
        //get all country ids
        List<String> listOfIds = jsonPath.getList("items.country_id");
        System.out.println("listOfIds = " + listOfIds);
        //get all cities where their country id is UK
        List<Object> citiesWithUK_ID = jsonPath.getList("items.findAll {it.country_id =='UK'}");
        List<Object> cityNamesWithUK_ID = jsonPath.getList("items.findAll {it.country_id =='UK'}.city");
        System.out.println("citiesWithUK_ID = " + citiesWithUK_ID);
        System.out.println("cityNamesWithUK_ID = " + cityNamesWithUK_ID);

    }
}
