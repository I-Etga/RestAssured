package com.cydeo.utilities;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

public abstract class ZipCodeTestBase {

    @BeforeAll
    public static void init(){

        baseURI="https://api.zippopotam.us";

    }

    @AfterAll
    public static void destroy(){

        reset();

    }
}