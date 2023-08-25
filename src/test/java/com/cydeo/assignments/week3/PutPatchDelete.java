package com.cydeo.assignments.week3;

import com.cydeo.pojo.Region;
import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PutPatchDelete extends HrTestBase {

    /*
     HOMEWORK 05
ORDS API DOCUMENT
—> https://documenter.getpostman.com/view/25449093/2s8ZDYYNBz
TASK 1:

—> POST a region then do GET same region to do validations.
Please use Map or POJO class, or JsonPath
Given accept is json
And content type is json
When I send post request to "/regions/" With json:
 {
"region_id":100,
"region_name":"Test Region" }
Then status code is 201
And content type is json
And region_id is 100
And region_name is Test Region
—> GET
Given accept is json
When I send GET request to "/regions/100" Then status code is 200
And content type is json
And region_id is 100
And region_name is Test Region
*/
    @DisplayName("POST a region and GET the same region")
    @Test
    void task1() {
// POST a Region
        //create body through String #1
        String regionBody = "{\n" +
                "        \"region_id\": 100,\n" +
                "        \"region_name\": \"Test Region\"\n" +
                "        }";

        //create body through map in java #2
        Map<String, Object> regionBodyMap = new LinkedHashMap<>();
        regionBodyMap.put("region_id", 100);
        regionBodyMap.put("region_name", "Test Region");

        //create body through POJO Class #3
        Region region = new Region();
        region.setRegion_name("Test Region");
        region.setRegion_id(100);

        //We can use all there as body while sending request

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .body(region)
                .when()
                .post("/regions/").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json").extract().jsonPath();

        //Verify body through POJO Class
        Region regionBodyPJ = jsonPath.getObject("", Region.class);
        assertEquals(100, regionBodyPJ.getRegion_id());
        assertEquals("Test Region", regionBodyPJ.getRegion_name());

        //Verify body through jsonPath
        assertEquals(100, jsonPath.getInt("region_id"));
        assertEquals("Test Region", jsonPath.getString("region_name"));

//GET the same Region
        jsonPath = given()
                .accept(ContentType.JSON)
                .when()
                .get("/regions/" + region.getRegion_id())
                .then()
                .statusCode(200)
                .contentType("application/json").extract().jsonPath();

        Region regionGET = jsonPath.getObject("", Region.class);
        System.out.println("regionGET = " + regionGET);

        assertEquals(region.getRegion_id(), jsonPath.getInt("region_id"));
        assertEquals(region.getRegion_name(), jsonPath.getString("region_name"));
    }

    /*
 TASK 2:
—-> PUT request then DELETE
Given accept type is Json
And content type is json
When I send PUT request to /regions/100 With json body: {
"region_id": 100,
"region_name": "Wooden Region"
}
Then status code is 200
And content type is json region_id is 100
region_name is Wooden Region
—> DELETE
Given accept type is Json
When I send DELETE request to /regions/100 Then status code is 200
*/
    @DisplayName("PUT a region and DELETE the same region")
    @Test
    void tasks2() {
        //PUT REQUEST

        // body as String  #1
        String requestBodySt = "{\n" +
                "        \"region_id\": 100,\n" +
                "        \"region_name\": \"Wooden Region\"\n" +
                "        }";

        //Body as Map #2
        Map<String, Object> regionsW = new LinkedHashMap<>();
        regionsW.put("region_id", 100);
        regionsW.put("region_name", "Wooden Region");

        ////Body as Pojo class #3
        Region regionRequestBody = new Region();
        regionRequestBody.setRegion_id(100);
        regionRequestBody.setRegion_name("Wooden Region");


        JsonPath jsonPath = given()
                .accept(ContentType.JSON) // I'll send json request
                .and()
                .body(regionsW)
                .contentType(ContentType.JSON) // I'll get json response
                .when()
                .put("/regions/" + regionsW.get("region_id")).prettyPeek()
                .then()
                .assertThat()
                .statusCode(200).extract().jsonPath();

        assertEquals(100, jsonPath.getInt("region_id"));
        assertEquals("Wooden Region", jsonPath.getString("region_name"));

        /*
        I know it's a little complicated but why sometimes region_id and sometimes items.region_id ??
        First of all, it depends on the documentation then the request and parameters we've given.
        In this case, if we get /regions then returns a json object starting with items:[{region}]
        However, if we get/put/patch /regions/100, it returns directly the region obj {region}
        That's I directly access data.
         */

        /*
        POST vs PUT
        Probably it depends on the app but normally if we send post request, new object created and id and order of object is given automatically
         But, when I send put request, I give the id as parameter. It'll be placed where I want !!
         Also, if there is a object at given id, the info of it will be changed according the info in body of put request !!
         */

        //DELETE REQUEST
        //Given accept type is Json
        //When I send DELETE request to /regions/100 Then status code is 200
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/regions/" + regionsW.get("region_id"))
                .then()
                .statusCode(200);

        // Verify if the deleted object still exists. [negative test] !!
        given()
                .accept(ContentType.JSON)
                .pathParam("id", regionsW.get("region_id"))
                .when()
                .get("/regions/{id}")
                .then()
                .statusCode(404);
        //No content as json when we get 404 !!
    }


    /*
 TASK 3:
—> POST a region then Database validations. Please use Map
Given accept is json
and content type is json
When I send post request to "/regions/" With json:
{
"region_id":200,
"region_name":"Test Region"
}
Then status code is 201
content type is json
When I connect to HR database and execute query "SELECT region_id,
region_name FROM regions WHERE region_id = 200" Then region_name from database should match region_name from POST request
—> DELETE
Given accept type is Json
When I send DELETE request to /regions/200 Then status code is 200

     */
    @DisplayName("POST a region, verify posted in DB and DELETE the same region")
    @Test
    void task3() throws SQLException {

        // request body as String
        String requestBody = "{\n" +
                "\"region_id\":200,\n" +
                "\"region_name\":\"Test Region\"\n" +
                "}";
        // request body as Map
        Map<String, Object> region = new LinkedHashMap<>();
        region.put("region_id", 200);
        region.put("region_name", "Test Region");

        // request body as Pojo
        Region regionReqBody = new Region();
        regionReqBody.setRegion_id(200);
        regionReqBody.setRegion_name("Test Region");

        given()
                .accept(ContentType.JSON)
                .body(region)
                .contentType(ContentType.JSON)
                .when()
                .post("/regions/").prettyPeek()
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);

        //When I connect to HR database and execute query "SELECT region_id,
        //region_name FROM regions WHERE region_id = 200" Then region_name from database should match region_name from POST request

        String dbUrl = "jdbc:oracle:thin:@52.23.200.192:1521:XE";
        String dbUsername = "hr";
        String dbPassword = "hr";

        //DriverManager class getConnection Method will help to connect database
        Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //It will help us to execute queries
        Statement statement = conn.createStatement();

        //ResultSet will store data after execution. It stores only data(there is no table info)
        ResultSet rs = statement.executeQuery("SELECT region_id, region_name FROM regions where region_id = 200");

        while (rs.next()) {
            assertEquals("200", rs.getString(1));
            assertEquals("Test Region", rs.getString(2));
        }

        //DELETE the same region

        given()
                .accept(ContentType.JSON)
                .pathParam("id", 200)
                .when()
                .delete("/regions/{id}")
                .prettyPeek()
                .then()
                .statusCode(200);


        // Verify if the deleted object still exists. [negative test] !! --> optional !!
        given()
                .accept(ContentType.JSON)
                .pathParam("id", 200)
                .when()
                .get("/regions/{id}")
                .then()
                .statusCode(404);
        //No content as json when we get 404 !!
    }

}
