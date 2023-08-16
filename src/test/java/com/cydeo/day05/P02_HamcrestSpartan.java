package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;

import static io.restassured.RestAssured.*;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class P02_HamcrestSpartan extends SpartanTestBase {
     /*
    Given accept type is Json
    And path param id is 15
    When user sends a get request to api/spartans/{id}
    Then status code is 200
    And content type is Json
    And json data has following
        "id": 15,
        "name": "Meta",
        "gender": "Female",
        "phone": 1938695106
     */

    @DisplayName("Get Single Spartan with Hamcrest")
    @Test
    public void test1() {
        given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 15)
                .when().get("api/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", is(15),
                        "name", is("Meta"),
                        "gender", equalTo("Female"),
                        "phone", is(1938695106));

    }

    @DisplayName("Get Single Spartan with Hamcrest with")
    @Test
    public void test2() {
        given()
                .accept(ContentType.JSON)
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .assertThat() //assertThat(),and() these are syntactic sugar just to increase readability
                .statusCode(200)
                .and()
                .contentType("application/json")
                .and()
                .assertThat()
                .body("id", is(15))
                .and()
                .body("name", is("Meta"))
                .body("gender", is("Female"))
                .body("phone", is(1938695106));

        //this test2() and test1() exactly the same testcase/ we just used more filler(syntactic sugar) keywords to increase readability

        //.statusCode(is(200)); --> this is optional to increase readability
    }

    @DisplayName("Get Single Spartan with Hamcrest LOGS")
    @Test
    public void test3() {
        Response response = given()
                .log().ifValidationFails() //to see request all information, if validation fails
                .accept(ContentType.JSON)
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}").prettyPeek() //returns response object. So we can use it here !!
                .then()
                .log().ifValidationFails(LogDetail.HEADERS) //to see response all information, if validation fails
                .statusCode(200)
                .contentType("application/json")
                .body("id", is(15),
                        "name", is("Meta"),
                        "gender", equalTo("Female"),
                        "phone", is(1938695106))
                .extract().response(); // Thanks to extract method we could be able to get response object !!

        int id = response.path("id");
        System.out.println("id = " + id);


             /*
            REQUEST
                given().
                        log().all() --> it will give all information about your request(path/query params,URI,body etc)
                        .method()
                        .uri()
                        .parameters()
                        .body()....
            RESPONSE
                then()
                        .log().all() --> it will give all response information

                ifValidationFails() --> it will print all information when there is a failure
                ifValidationFails(LogDetail.HEADERS) --> after failure only show me headers
                we can change LogDetail scope to see other information.
                and we can use ifValidationFails for both request and response logs.
         */

        /*
            HOW TO PRINT RESPONSE BODY

                response.prettyPrint() (String) --> it is printing response body into screen
                response.prettyPeek() (Response) --> it will print response intro screen, returns Response
                and allows us to CONTINUE CHAINING  !!
         */
        /*
            HOW TO EXTRACT DATA AFTER DOING VALIDATION WITH THEN() and HAMCREST?
            -- extract() --> method will help us to STORE data after doing verification
            in following type
                // Way #1
                response() --> to get response object ex: extract().response();
                OR
                // Way #2
                jsonPath() --> to get response body as jsonpath object directly
                   ex: extract().jsonPath();
         */
    }
    @DisplayName("Get Single Spartan with Hamcrest with Extract")
    @Test
    public void test4(){
        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", is(15),
                        "name", is("Meta"),
                        "gender", equalTo("Female"),
                        "phone", is(1938695106))
                .extract().jsonPath();

        //below lines are assume
        //api actual data (id,name)
        int id = jsonPath.getInt("id");
        String name = jsonPath.getString("name");
        System.out.println("id = " + id);

        //expected data from database
        //using db utils, saving variables
        int expectedIdDb = 15;
        String expectedNameDb = "Meta";

        //compare api vs database
        //we can use hamcrest or junit5 assertion
        assertThat(id,is(expectedIdDb));
        assertThat(name,is(equalTo(expectedNameDb)));

        //junit 5
        assertEquals(expectedIdDb,id);
        assertEquals(expectedNameDb,name);


        /*
            Why we need to extract, while we can complete all the verification(status code,header,body)
            with then() and hamcrest mathcers?

            --Assume that we are going to do verification against DB/UI. In that case I need to get data from API
                After completing my api verification
                So we need to sometimes list of names / ids / whatever field we have to check against to db or UI
                That is why we need to initialize as Response or JsonPath to get related data from api that we want to verify.

         */

    }
}
