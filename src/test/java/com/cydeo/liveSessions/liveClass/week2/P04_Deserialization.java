package com.cydeo.liveSessions.liveClass.week2;

import com.cydeo.pojo.Product;
import com.cydeo.utilities.FruitTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P04_Deserialization extends FruitTestBase {
    /**
     * Send request to FruitAPI url and save the response
     * Accept application/json
     * GET /customers
     * Store the response in Response Object that comes from get Request
     * Print out followings
     * - Print response
     * - Content-Type is application/json
     * - Status Code is 200
     * - Retrieve data as JAVA Collections and print out following information
     * <p>
     * System.out.println("====== GET META ======");
     * System.out.println("====== GET LIMIT ======");
     * System.out.println("====== GET CUSTOMERS ======");
     * System.out.println("====== GET FIRST CUSTOMER ======");
     * System.out.println("====== PRINT CUSTOMERS IDs ======");
     * System.out.println("====== PRINT CUSTOMERS Names ======");
     */

    @Test
    void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/customers");


        JsonPath jsonPath = response.jsonPath();

        Map<String, Object> allData = jsonPath.getMap("");
        System.out.println(allData);


        System.out.println("====== GET META ======"); //
        Map<String, Integer> meta = (Map<String, Integer>) allData.get("meta");
        System.out.println(meta);

        System.out.println("====== GET LIMIT ======");
        System.out.println("meta.get(\"limit\") = " + meta.get("limit"));

        System.out.println("====== GET CUSTOMERS ======");
        List<Map<String, Object>> allCustomers = (List<Map<String, Object>>) allData.get("customers");
        System.out.println(allCustomers);

        System.out.println("====== GET FIRST CUSTOMER ======");
        Map<String, Object> firstCustomer = allCustomers.get(0);
        System.out.println(allCustomers.get(0));

        System.out.println("====== PRINT FIRST CUSTOMER IDs ======");
        System.out.println(firstCustomer.get("id"));
        assertEquals(6, firstCustomer.get("id"));

        System.out.println("====== PRINT CUSTOMERS IDs ======");
        List<Integer> allIDs = allCustomers.stream().map(eachCustomer -> (Integer) eachCustomer.get("id")).collect(Collectors.toList());
        System.out.println(allIDs);

    }
}
