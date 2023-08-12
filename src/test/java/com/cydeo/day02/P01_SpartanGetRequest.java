package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SpartanGetRequest {
    String url = "http://54.173.124.216:8000";

    /*

     * Given content type is application/son
     * When User sends GET request /api/spartans endpoint
     * Then status code should be 200
     * And Content type should be application/json

     */

    @Test
    public void getAllSpartans() {

        Response response = RestAssured.given().accept(ContentType.JSON) // hey api please send me json response
                .when()
                .get(url + "/api/spartans");

        //print the response body
        response.prettyPrint();

        int actualStatusCode = response.statusCode();

        Assertions.assertEquals(200, actualStatusCode);

        //How to get response content type header ?

        String actualContentType = response.contentType();
        System.out.println(actualContentType);

        //assert the contentType
        Assertions.assertEquals("application/json", actualContentType);

        //how to get Connection header value ?
        System.out.println(response.header("Content-type"));
        System.out.println(response.header("Connection"));
        System.out.println(response.header("Date"));

        //how to verify header exists?
        // headers() & hasHeaderWithName() methods help us to verify header exists or not
        //it is useful for dynamic header values like Date, we are only verifying header exist or not
        boolean isDate = response.headers().hasHeaderWithName("Date");

        Assertions.assertTrue(isDate);


    }
    /*

     * Given content type is application/son
     * When user sends GET request /api/spartans/3 endpoint
     * Then status code should be 200
     * And Content type should be application/json
     * And response body needs to contain Fidole

     */

    @Test
    public void getSpartan() {
        Response response = RestAssured.given().accept(ContentType.JSON) // hey api please send me json response
                .when()
                .get(url + "/api/spartans/3");

        //
        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200, actualStatusCode);


        //verify content type is json
        String actualContentType = response.contentType();
        //String actualContentType = response.getContentType();
        Assertions.assertEquals("application/json", actualContentType);
        Assertions.assertEquals(ContentType.JSON.toString(), actualContentType);
        Assertions.assertEquals(ContentType.JSON.toString(), response.header("Content-type"));


        response.prettyPrint();

        //Verify body contains "Fidole"
        Assertions.assertTrue(response.body().asString().contains("Fidole"));

         /*
            This is not a good way to make assertion. In this way we are just converting response to String and
            with the help of String contains we are just looking into Response.But we should be able to get json
            "name" key value then verify that one is equal to "Fidole"
         */
    }

    /*
     * Given no headers provided
     * When Users send GET request to /api/hello
     * Then response status code should be 200
     * And Content type header should be "text/plain;charset=UTF-8"
     * And header should contain Date
     * And Content-Length should be 17
     * And body should be "Hello from Sparta"
     */

    @Test
    public void getHello() {
        Response response = RestAssured.given().get(url + "/api/hello");

        //print result on the console
        response.prettyPrint();

        //verify status code
        Assertions.assertEquals(200, response.statusCode());

        //And Content type header should be "text/plain;charset=UTF-8"
        response.contentType();
        response.getContentType();
        Assertions.assertEquals("text/plain;charset=UTF-8", response.contentType());

        //And header should contain Date
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

        //And Content-Length should be 17
        Assertions.assertEquals("17", response.header("Content-Length"));

        //And body should be "Hello from Sparta"
        Assertions.assertTrue(response.getBody().asString().equals("Hello from Sparta"));

    }
}

