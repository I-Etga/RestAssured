package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class P04_SpartanFlow extends SpartanTestBase {
    /*

   Create a Spartan Flow to run below testCases dynamically

  - POST     /api/spartans
           Create a spartan Map,Spartan class
               name = "API Flow POST"
               gender="Male"
               phone=1231231231l

           - verify status code 201
           - message is "A Spartan is Born!"
           - take spartanID from response and save as a global variable
     */

    public Spartan spartan = new Spartan();
    public static String name = "API Flow POST";
    public static String requestStr = "/api/spartans/{id}";
    public static int id;

    /*
    If you want to keep a variable updated while executing test , You have to define it as static !!
    Otherwise, for every test you'll get the default value of the variable.

    Also, there are at least two way to order tests.
    #1 @Order() Annotation #2 @DisplayName("1")
     */

    @Order(1)
    @DisplayName("POST a spartan")
    @Test()
    public void test1() {

        spartan.setName("API Flow POST");
        spartan.setGender("Male");
        spartan.setPhone(1231231231l);


        Response response = given().log().body()
                .accept(ContentType.JSON) // please send me JSON RESPONSE
                .and()
                .contentType(ContentType.JSON) // I send you JSON REQUEST BODY
                .body(spartan) // automatically serialized !!
                .when()
                .post("/api/spartans/").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!")).extract().response();

        id = response.path("data.id");
    }

    /*
    - GET  Spartan with spartanID     /api/spartans/{id}
             - verify status code 200
             - verify name is API Flow POST
*/
    @Order(2)
    @DisplayName("GET the posted spartan")
    @Test
    public void test2() {
        System.out.println("id = " + id);
        given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get(requestStr)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));
    }

    /*
    - PUT  Spartan with spartanID    /api/spartans/{id}

             Create a spartan Map
                name = "API PUT Flow"
                gender="Female"
                phone=3213213213l

             - verify status code 204
*/
    @Order(3)
    @DisplayName("PUT a spartan")
    @Test
    public void test3() {
        spartan.setName("API Flow PUT");
        spartan.setGender("Female");
        spartan.setPhone(3213213213l);

        given()
                .accept(ContentType.JSON)
                .body(spartan)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .put(requestStr)
                .then()
                .statusCode(204);
    }

    /*
    - GET  Spartan with spartanID     /api/spartans/{id}
             - verify status code 200
             - verify name is API PUT Flow
*/
    @Order(4)
    @DisplayName("GET the spartan")
    @Test
    public void test4() {
        name = "API Flow PUT";

        given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get(requestStr)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));
    }

    /*
    - DELETE  Spartan with spartanID   /api/spartans/{id}
             - verify status code 204
*/
    @Order(5)
    @DisplayName("DELETE the spartan[put]")
    @Test
    public void test5() {
        given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete(requestStr)
                .then()
                .statusCode(204);
    }

    /*
     - GET  Spartan with spartanID   /api/spartans/{id}
             - verify status code 404

*/
    @Order(6)
    @DisplayName("GET the deleted spartan")
    @Test
    public void test6() {
        given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get(requestStr)
                .then()
                .statusCode(404);

    }
    /*
    Challenges
       Create @Test annotated method for each Request
       Put them in order to execute as expected
                    - Use your googling skills
                    - How to run Junit5 Tests in order  ?

     */
}
