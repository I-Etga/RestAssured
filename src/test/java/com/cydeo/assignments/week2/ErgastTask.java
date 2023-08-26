package com.cydeo.assignments.week2;

import com.cydeo.pojo.ConstructorPJ;
import com.cydeo.pojo.Driver;
import com.cydeo.utilities.ErgastTestBase;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ErgastTask extends ErgastTestBase {

    /*
        BASE URL —> http://ergast.com/api/f1/
        TASK 1 : Solve same task with 4 different way
- Given accept type is json
- And path param driverId is alonso
- When user send request /drivers/{driverId}.json
- Then verify status code is 200
- And content type is application/json; charset=utf-8 - And total is 1
- And givenName is Fernando
- And familyName is Alonso
- And nationality is Spanish
     */


    @DisplayName("")
    @Test
    void test1() {

        System.out.println("-- #1 Use HAMCREST MATCHERS --");
        Response response = given().accept(ContentType.JSON)
                .pathParam("driverId", "alonso")
                .when()
                .get("/drivers/{driverId}.json")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("MRData.total", equalTo("1"))
                .body("MRData.DriverTable.Drivers[0].givenName", equalTo("Fernando"))
                .body("MRData.DriverTable.Drivers[0].familyName", equalTo("Alonso"))
                .body("MRData.DriverTable.Drivers[0].nationality", equalTo("Spanish"))
                .extract().response();

        System.out.println("response.path(\"MRData.DriverTable.Drivers[0].givenName\") = " + response.path("MRData.DriverTable.Drivers[0].givenName"));

        JsonPath jsonPath = response.jsonPath();

        System.out.println("-- #2 Use JSONPATH --");
        String total = jsonPath.getString("MRData.total");
        String givenName = jsonPath.getString("MRData.DriverTable.Drivers[0].givenName");
        String familyName = jsonPath.getString("MRData.DriverTable.Drivers[0].familyName");
        String nationality = jsonPath.getString("MRData.DriverTable.Drivers[0].nationality");


        assertThat(total, is("1"));
        assertThat(givenName, equalTo("Fernando"));
        assertThat(familyName, equalTo("Alonso"));
        assertThat(nationality, equalTo("Spanish"));

        System.out.println("-- #3 Convert Driver information to Java Structure --");

        Map<String, Object> driverMap = jsonPath.getMap("MRData.DriverTable.Drivers[0]");
        assertThat(driverMap.get("givenName"), is("Fernando"));
        assertThat(driverMap.get("familyName"), equalTo("Alonso"));
        assertThat(driverMap.get("nationality"), is("Spanish"));


        System.out.println("-- #4 Convert Driver information POJO Class --");

        Driver driver = jsonPath.getObject("MRData.DriverTable.Drivers[0]", Driver.class);
        assertThat(driver.getDriverId(), is("alonso"));
        assertThat(driver.getGivenName(), is("Fernando"));
        assertThat(driver.getFamilyName(), is("Alonso"));
        assertThat(driver.getNationality(), is("Spanish"));

    }

    /*
     BASE URL —> http://ergast.com/api/f1/
            TASK-2
—> There is a class in JAVA That’s why give your class name ConstrutorPOJO to separate from existing one. Wrong imports may cause issue
- Given accept type is json
- When user send request /constructorStandings/1/constructors.json - Then verify status code is 200
- And content type is application/json; charset=utf-8
- And total is 17
- And limit is 30
- And each constructor has constructorId
- And each constructor has name
- And size of constructor is 17
- And constructor IDs has “benetton”, “mercedes”,”team_lotus”
     */
    @Test
    void task2() {
        System.out.println("-- #1 Use HAMCREST MATCHERS --");

        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/constructorStandings/1/constructors.json")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("MRData.total", is("17"))
                .body("MRData.limit", equalTo("30"))
                .body("MRData.ConstructorTable.Constructors.constructorId", everyItem(notNullValue()))
                .body("MRData.ConstructorTable.Constructors.name", everyItem(notNullValue()))
                .body("MRData.ConstructorTable.Constructors", hasSize(17))
                .body("MRData.ConstructorTable.Constructors.constructorId", hasItems("benetton", "mercedes", "team_lotus"))
                .extract().response().prettyPeek();

        JsonPath jsonPath = response.jsonPath();

        System.out.println("-- #2 Use JSONPATH --");

        String total = jsonPath.getString("MRData.total");
        String limit = jsonPath.getString("MRData.limit");
        List<Map<String, Object>> listOfConstructors = jsonPath.getList("MRData.ConstructorTable.Constructors");
        List<String> listOfConstrId = jsonPath.getList("MRData.ConstructorTable.Constructors.constructorId");
        List<String> listOfNames = jsonPath.getList("MRData.ConstructorTable.Constructors.name");

        assertThat(total, is("17"));
        assertThat(limit, equalTo("30"));
        assertThat(listOfConstructors, hasSize(17));
        assertThat(listOfConstrId, everyItem(notNullValue()));
        assertThat(listOfNames, everyItem(notNullValue()));
        assertThat(listOfConstrId, hasItems("benetton", "mercedes", "team_lotus"));

        System.out.println("-- #3 Convert Driver information to Java Structure --");

        List<Map<String, Object>> constructorMap = jsonPath.getList("MRData.ConstructorTable.Constructors");
        assertThat(constructorMap, hasSize(17));

        List<Object> ids = new ArrayList<>();
        for (Map<String, Object> each : constructorMap) {
            assertThat(each.get("constructorId"), notNullValue());
            assertThat(each.get("name"), notNullValue());
            ids.add(each.get("constructorId"));
        }
        assertThat(ids, hasItems("benetton", "mercedes", "team_lotus"));

        System.out.println("-- #4 Convert Driver information POJO Class --");

        List<ConstructorPJ> constructorPJS = jsonPath.getList("MRData.ConstructorTable.Constructors", ConstructorPJ.class);

        assertThat(constructorPJS, hasSize(17));

        List<Object> idsPJ = new ArrayList<>();
        for (ConstructorPJ constructorPJ : constructorPJS) {
            assertThat(constructorPJ.getName(), notNullValue());
            assertThat(constructorPJ.getConstructorId(), notNullValue());
            idsPJ.add(constructorPJ.getConstructorId());
        }

        assertThat(idsPJ, hasItems("benetton", "mercedes", "team_lotus"));

        //extra --> get the first constructor !!
        ConstructorPJ constructorPJ1 = jsonPath.getObject("MRData.ConstructorTable.Constructors[0]", ConstructorPJ.class);
        System.out.println("constructor1.getConstructorId() = " + constructorPJ1.getConstructorId());
        System.out.println("constructor1.getName() = " + constructorPJ1.getName());

        ConstructorPJ constructorPJ6 = jsonPath.getObject("MRData.ConstructorTable.Constructors[5]", ConstructorPJ.class);
        System.out.println("constructor6.getConstructorId() = " + constructorPJ6.getConstructorId());
        System.out.println("constructor6.getName() = " + constructorPJ6.getName());

    }
}
