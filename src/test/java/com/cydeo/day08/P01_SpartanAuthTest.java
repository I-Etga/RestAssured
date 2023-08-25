package com.cydeo.day08;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class P01_SpartanAuthTest extends SpartanAuthTestBase {

    @DisplayName("GET api/spartans as GUEST user --> EXPECT --> 401")
    @Test
    public void test1() {

        given().
                accept(ContentType.JSON)
                .when()
                .get("/api/spartans")
                .then()
                .statusCode(401)
                .body("error", is("Unauthorized"));
    }

    @DisplayName("GET api/spartans as USER --> EXPECT --> 200")
    @Test
    public void test2() {

        given().
                accept(ContentType.JSON)
                .auth().basic("user", "user")
                .when()
                .get("/api/spartans")
                .then()
                .statusCode(200);
    }

    @DisplayName("DELETE api/spartans/{id} as EDITOR --> EXPECT --> 403 FORBIDDEN")
    @Test
    public void test3() {

        given().
                accept(ContentType.JSON)
                .auth().basic("editor", "editor")
                .pathParam("id", 45)
                .when()
                .delete("/api/spartans/{id}").prettyPeek()
                .then()
                .statusCode(403)
                .body("error", is("Forbidden"));
    }

    @DisplayName("DELETE api/spartans/{id} as ADMIN --> EXPECT --> 204 ")
    @Test
    public void test4() {

        given().
                accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id", 100)
                .when()
                .delete("/api/spartans/{id}").prettyPeek()
                .then()
                .statusCode(204);
    }
}
