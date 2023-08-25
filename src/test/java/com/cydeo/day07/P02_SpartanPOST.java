package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P02_SpartanPOST extends SpartanTestBase {

    /*
    Given accept tupe is JSON
And Content type is JSON
And request json body is:
{
"gender": "Male",
"name": "John Doe",
"phone": 8877445596
}
When user sends POST request to '/api/spartans'
Then status code 201
And content tupe should be application/json
And json payload/response/body should contain:
verify the success value is A Spartan is Born!
"name": "John Doe"
"gender": "Male",
"phone": 1231231231
     */


    @DisplayName("POST Spartan with String body")
    @Test
    public void test1() {

        String requestBody = "{\n" +
                "\"gender\": \"Male\",\n" +
                "\"name\": \"John Doe\",\n" +
                "\"phone\": 8877445596\n" +
                "}";

        JsonPath jsonPath = given().
                accept(ContentType.JSON) // please send me JSON RESPONSE
                .and()
                .contentType(ContentType.JSON) // I send you JSON REQUEST BODY
                .body(requestBody)
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath();

        //request body verification
        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(8877445596L, jsonPath.getLong("data.phone"));

        //I want to get id out of the response body, to delete or sen get request later on

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);
    }

    @DisplayName("POST Spartan with Map body")
    @Test
    public void test2() {

        Map<String, Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name", "John Doe");
        requestBodyMap.put("gender", "Male");
        requestBodyMap.put("phone", "8877445596");

        //We've just created a Map and put the info that we want to send as a JSON REQUEST BODY
        System.out.println("requestBodyMap = " + requestBodyMap);

        JsonPath jsonPath = given().log().body()
                .accept(ContentType.JSON) // please send me JSON RESPONSE
                .and()
                .contentType(ContentType.JSON) // I send you JSON REQUEST BODY
                .body(requestBodyMap) // automatically serialized !!
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath();

        //request body verification
        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(8877445596L, jsonPath.getLong("data.phone"));

        //I want to get id out of the response body, to delete or sen get request later on

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);

        //Can we have SpartanUtil class which is giving as a requestMap with dynamic dummy data
        //using faker library?
    }

    @DisplayName("POST Spartan with Spartan POJO")
    @Test
    public void test3() {

        Spartan spartan = new Spartan();
        spartan.setName("Harold Finch");
        spartan.setGender("Male");
        spartan.setPhone(1234567890L);

        System.out.println("spartan = " + spartan);


        JsonPath jsonPath = given().log().body()
                .accept(ContentType.JSON) // please send me JSON RESPONSE
                .and()
                .contentType(ContentType.JSON) // I send you JSON REQUEST BODY
                .body(spartan) // automatically serialized !!
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath();

        //request body verification
        assertEquals("Harold Finch", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(1234567890L, jsonPath.getLong("data.phone"));

        //I want to get id out of the response body, to delete or sen get request later on

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);
    }

    @DisplayName("POST Spartan with Spartan POJO and GET same Spartan")
    @Test
    public void test4() {

        // empty spartan object, and we use setters to set some value
        //values can be from faker library on somewhere else which is changing dynamically
        Spartan spartanPOST = new Spartan();
        spartanPOST.setName("Harold Finch");
        spartanPOST.setGender("Male");
        spartanPOST.setPhone(1234567890L);
        spartanPOST.setId(500); // even if we put some id value, it wouldn't be serialized because of jackson

        System.out.println("spartanPOST = " + spartanPOST);


        JsonPath jsonPath = given().log().body()
                .accept(ContentType.JSON) // please send me JSON RESPONSE
                .and()
                .contentType(ContentType.JSON) // I send you JSON REQUEST BODY
                .body(spartanPOST) // automatically serialized !!
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath();

        //request body verification
        assertEquals("Harold Finch", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(1234567890L, jsonPath.getLong("data.phone"));

        //I want to get id out of the response body, to delete or sen get request later on

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);

        //SEND GET REQUEST TO THE SPARTAN THAT IS CREATED THEN DESERIALIZE TO SPARTAN CLASS AND COMPARE !!

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200).extract().response();

        Spartan spartanGET = response.as(Spartan.class);
        System.out.println("spartanGET = " + spartanGET);
    }

}
