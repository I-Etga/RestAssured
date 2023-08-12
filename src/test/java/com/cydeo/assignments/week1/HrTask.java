package com.cydeo.assignments.week1;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class HrTask extends HrTestBase {


    /*
    Task 1 :
- Given accept type is Json
- When users sends request to /countries/US
- Then status code is 200
- And Content - Type is application/json
- And response contains United States of America
     */
    @DisplayName("GET Request /countries/US")
    @Test
    void test1() {

        Response response = given().accept(ContentType.JSON)
                .when().get("/countries/US");

        response.prettyPrint();
        //Then status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //And Content - Type is application/json
        assertEquals("application/json", response.contentType());

        //And response contains United States of America
        assertTrue(response.body().asString().contains("United States of America"));

        //better practice
        assertEquals("United States of America", response.path("country_name"));

    }

    /*
    Task 2 :
- Given accept type is Json
- When users sends request to /employees/1
- Then status code is 404
     */
    @DisplayName("GET Request /employees/1 ")
    @Test
    void test2() {

        Response response = given().accept(ContentType.JSON)
                .when().get("/employees/1");

        response.prettyPrint();

        //Then status code is 404
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }

    /*
    Task 3 :
- Given accept type is Json
- When users sends request to /regions/1
- Then status code is 200
- And Content - Type is application/json
- And response contains Europe
- And header should contains Date
- And Transfer-Encoding should be chunked
     */

    @DisplayName("GET Request /regions/1")
    @Test
    void test3() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/regions/1");

        response.prettyPrint();

        //Then status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //And Content - Type is application/json
        assertEquals("application/json", response.contentType());

        //And response contains Europe
        assertTrue(response.body().asString().contains("Europe"));
        assertTrue(response.path("region_name").toString().contains("Europe"));

        //And header should contains Date
        assertTrue(response.headers().hasHeaderWithName("Date"));

        //And Transfer-Encoding should be chunked
        assertEquals("chunked", response.getHeader("Transfer-Encoding"));

        //getHeader() or header() returns the value of header as String
        System.out.println(response.header("ETag"));
        System.out.println(response.header("Content-Type"));
        System.out.println(response.header("Date"));
        System.out.println(response.header("Transfer-Encoding"));

        //gerHeaders() return all headers as header data type !!
        for (Header eachHeader : response.getHeaders()) {
            System.out.println(eachHeader.getName() + " = " + eachHeader);
        }
    }

    /*TASK 4 :
- Given accept type is Json
- Path param country_id value-US
- When users sends request to /countries/{country_id}
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
And Region_id is 2
     */

    @DisplayName("GET Request /countries with 'region_id'")
    @Test
    void test4() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("country_id", "US")
                .when()
                .get("/countries/{country_id}");

        response.prettyPrint();
        //Then status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //And Content - Type is Json
        assertEquals("application/json", response.contentType());

        //And country_id is US
        assertEquals("US", response.path("country_id"));

        //And Country_name is United States of America
        assertEquals("United States of America", response.path("country_name"));

        //And Region_id is 2
        assertEquals("2", response.path("region_id").toString());
    }

    /*
    TASK 5 :
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
     */
    @DisplayName("GET Request /employees with query param q={\"department_id\":80}")
    @Test
    void test5() {

        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");

        response.prettyPrint();

        //Then status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //And Content - Type is Json
        assertEquals("application/json", response.contentType());

        //And all job_ids start with 'SA'

        List<String> job_ids = response.path("items.job_id");
        for (String jobID : job_ids) {
            assertTrue(jobID.startsWith("SA"));
        }

        //- And all department_ids are 80
        List<Integer> department_ids = response.path("items.department_id");
        for (Integer departmentID : department_ids) {
            assertEquals(80, departmentID);
        }

        //- Count is 25
        assertEquals("25", response.path("count").toString());
    }

    /*

    TASK 3 :
    - Given accept type is Json
    - Query param value q={region_id":3}
    - When users sends request to /countries
    - Then status code is 200
    - And all regions_id is 3
    - And count is 6
    - And hasMore is false
    - And Country_name are;
    Australia,China,India,Japan,Malaysia,Singapore
     */
    @DisplayName("GET Request/countries with query param q={region_id\":3}")
    @Test
    void test6() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":3}")
                .when()
                .get("/countries");

        response.prettyPrint();

        //  - Then status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //    - And all region_id is 3
        List<Integer> region_ids = response.path("items.region_id");
        for (Integer regionId : region_ids) {
            assertEquals(3, regionId);
        }

        //    - And count is 6
        assertEquals("6", response.path("count").toString());

        //    - And hasMore is false
        assertEquals("false", response.path("hasMore").toString());

        //    - And Country_name are;
        //    Australia,China,India,Japan,Malaysia,Singapore

        List<String> actualCountryNames = response.path("items.country_name");
        List<String> expectedCountryNames = Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");

        assertEquals(expectedCountryNames, actualCountryNames);

        //alternative way#2
        assertEquals(Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore"), actualCountryNames);


    }
}
