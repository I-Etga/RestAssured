package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ErgastTestBase {

    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://ergast.com/api/f1/";

    }
}
