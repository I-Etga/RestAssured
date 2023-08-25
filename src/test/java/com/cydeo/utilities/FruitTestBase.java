package com.cydeo.utilities;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class FruitTestBase {

    @BeforeAll
    public static void init() {

        baseURI = "https://api.predic8.de/shop/v2";

    }

    @AfterAll
    public static void destroy() {
        reset();
    }


}