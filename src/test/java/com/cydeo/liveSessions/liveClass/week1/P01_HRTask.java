package com.cydeo.liveSessions.liveClass.week1;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01_HRTask extends HrTestBase {
    /**
     * TASK 2 :
     * - Given accept type is Json
     * - Query param value - q={"department_id":80}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And Content - Type is Json
     * - And all job_ids start with 'SA'
     * - And all department_ids are 80
     * - Count is 25
     */
    @Test
    public void task2() {

        Response response = given().log().uri().accept(ContentType.JSON)
                .queryParam("q", "{\"department_id\":80}").
                when().get("/employees").prettyPeek();


        //     * - Then status code is 200
        assertEquals(200, response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //     * - And Content - Type is Json
        assertEquals("application/json", response.contentType());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //     * - And all job_ids start with 'SA'
        List<String> allJobIDs = response.path("items.job_id");
        System.out.println(allJobIDs);

        for (String eachJOBID : allJobIDs) {
            System.out.println(eachJOBID);
            assertTrue(eachJOBID.startsWith("SA"));
        }


        //     * - And all department_ids are 80
        List<Integer> allIDS = response.path("items.department_id");

        for (Integer eachID : allIDS) {
            System.out.println(eachID);
            assertEquals(80, eachID);
        }

        //     * - Count is 25
        int count = response.path("count");
        System.out.println(count);

        assertEquals(25, count);


    }

}
