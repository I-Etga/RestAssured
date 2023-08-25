package com.cydeo.day08;

import com.cydeo.utilities.BookItTestBase;
import com.cydeo.utilities.BookItUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class P02_BookItTest extends BookItTestBase {

    //String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxODYwOCIsImF1ZCI6InN0dWRlbnQtdGVhbS1tZW1iZXIifQ.quXoSnCD0JyO2Wxq-PQ9pMmBEB_CBP8gLg1uE3yWFOE";

    String email = "raymond@cydeo.com";
    String password = "abs123";
    String accessToken = BookItUtils.getToken(email,password);

    @DisplayName("GET /api/campuses")
    @Test
    void test1() {

        System.out.println("accessToken = " + accessToken);

        given().
                accept(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .get("/api/campuses").prettyPeek()
                .then().statusCode(200);

    }

    @DisplayName("GET /api/users/me")
    @Test
    void test2() {

        given().
                accept(ContentType.JSON)
                .header("Authorization", BookItUtils.getToken(email,password))
                .when()
                .get("/api/users/me").prettyPeek()
                .then().statusCode(200);

    }

    @DisplayName("GET /api/users/me")
    @Test
    void test3() {

        given().
                accept(ContentType.JSON)
                .auth().oauth2(accessToken)
                .when()
                .get("/api/users/me").prettyPeek()
                .then().statusCode(200);

    }
}
