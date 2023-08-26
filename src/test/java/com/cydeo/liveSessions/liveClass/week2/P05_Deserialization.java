package com.cydeo.liveSessions.liveClass.week2;

import com.cydeo.pojo.MRData;
import com.cydeo.pojo.Status;
import com.cydeo.pojo.StatusTable;
import com.cydeo.utilities.ErgastTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class P05_Deserialization extends ErgastTestBase {
      /*
        - ERGAST API
        - Given accept type is json
        - When user send request /status.json
        - Then verify status code is 200
        - And content type is application/json; charset=utf-8
        - And total is 137
        - And limit is 30
        - And each status has statusId
     */

    @Test
    void test1() {
        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when()
                .get("/status.json").prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract().jsonPath();

        // DO DESERIALIZATION
        MRData mrData = jsonPath.getObject("MRData", MRData.class);
        System.out.println(mrData);


        System.out.println(" --- GET ME STATUSTABLE ------");
        StatusTable statusTable = mrData.getStatusTable();
        System.out.println(statusTable);

        System.out.println(" --- GET ME STATUS LIST  ------");
        List<Status> statusList = statusTable.getStatusList();
        System.out.println(statusList);

        System.out.println(" --- GET ME FIRST STATUS   ------");
        System.out.println(statusList.get(0));

        System.out.println(" --- GET ME FIRST ID   ------");
        System.out.println(statusList.get(0).getStatusId());
    }
}
