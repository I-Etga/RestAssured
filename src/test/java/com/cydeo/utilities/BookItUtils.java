package com.cydeo.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

public class BookItUtils {

    public static String getToken(String email, String password) {

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .queryParam("email", email)
                .queryParam("password", password)
                .when()
                .get("/sign")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        //get the key out of json body
        String accessToken = jsonPath.getString("accessToken");

        return "Bearer " + accessToken;
    }
    /*
       the proper way to handle different token is
       you need to have each user type in your configuration.properties
       then one method where you pick userType and it will return token to you.

       getTokenByUser(String userType) "TEAM_LEADER","TEAM_MEMBER","TEACHER"

       switch(userType)
       case "TEAM_LEADER":
          String email = ConfigurationReader.getProperty("team-leader-email");
          String password = ConfigurationReader.getProperty("team-leader-password");

          .
          .
          .

          send request with given and email and password

               JsonPath jsonPath = given().accept(ContentType.JSON)
              .queryParam("email", email)
              .queryParam("password", password)
              .when()
              .get("/sign")
              .then()
              .statusCode(200)
              .extract().jsonPath();

      //get the key out of json body
      String accessToken = jsonPath.getString("accessToken");

      return "Bearer "+accessToken;

   */
    //teacher / student-member / student-leader
    public static String getTokenByRole(String role) {
        String email = "";
        String password = "";

        switch (role) {
            case "teacher":
                email = ConfigurationReader.getProperty("teacher_email");
                password = ConfigurationReader.getProperty("teacher_password");
                break;

            case "student-member":
                email = ConfigurationReader.getProperty("team_member_email");
                password = ConfigurationReader.getProperty("team_member_password");
                break;
            case "student-leader":
                email = ConfigurationReader.getProperty("team_leader_email");
                password = ConfigurationReader.getProperty("team_leader_password");
                break;
            default:

                throw new RuntimeException("Invalid Role Entry :\n>> " + role +" <<");
        }

        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        String accessToken = given()
                .queryParams(credentials)
                .when().get( "/sign").path("accessToken");

        return  "Bearer " + accessToken;

    }

    //Create one req spec for bookitUtils which accepts role
    public static RequestSpecification getReqSpec(String role){

        return given().
                log().all()
                .accept(ContentType.JSON)
                .header("Authorization", getTokenByRole(role));
    }


    //Create one res spec for status code and json verification

    public static ResponseSpecification getResSpec(int statusCode){
        return expect().statusCode(statusCode)
                .contentType(ContentType.JSON);
    }
}
