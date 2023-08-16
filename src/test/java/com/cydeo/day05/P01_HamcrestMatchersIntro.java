package com.cydeo.day05;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class P01_HamcrestMatchersIntro {

    @Test
    public void numbers() {

        //junit 5 assert equals method
        assertEquals(9, 6 + 3);

        //Hamcrest Matchers
        //Matchers has overloaded methods
        //-First one will take value to check
        //Second one will take another Matchers to make it readable / to add new assert functionality


        assertThat(6 + 3, is(9));
        assertThat(6 + 3, is(equalTo(9)));
        assertThat(6 + 3, equalTo(9));

        /**
         * is (someValue)
         * is(equalTo(someValue))
         * equalTo(someValue)
         All of them some in terms of assertion
         */

        //They're all the same for assertion !!
        assertThat(5 + 5, not(9));
        assertThat(5 + 5, is(not(9)));
        assertThat(5 + 5, is(not(equalTo(9))));


        assertThat(5 + 6, is(greaterThan(11)));
        assertThat(5 + 6, greaterThan(10));
        assertThat(5 + 6, lessThan(12));
        assertThat(5 + 6, lessThanOrEqualTo(11));

    }

    @Test
    void testStrings() {
        String msg = "API is fun!";

        assertThat(msg, is("API is fun!"));
        assertThat(msg, equalTo("API is fun!"));
        assertThat(msg, equalToIgnoringCase("api is fun!"));

        assertThat(msg, startsWith("API"));
        assertThat(msg, startsWithIgnoringCase("API"));

        assertThat(msg, endsWith("fun!"));
        assertThat(msg, endsWithIgnoringCase("FUN!"));

        assertThat(msg, containsString("is"));
        assertThat(msg, containsStringIgnoringCase("IS"));

        assertThat(msg, not("FUN!"));
        assertThat(msg, is(not("FUN!")));
    }

    @Test
    void testCollections() {

        List<Integer> numberList = Arrays.asList(3, 5, 1, 77, 44, 76);

        // how to check size of elements
        assertThat(numberList, hasSize(6));

        //how to check 77 us into the collection
        assertThat(numberList, hasItem(77));

        //how to check 44 and 76 is into the collection [-s]
        assertThat(numberList, hasItems(44, 76, 5));
        //assertThat(numberList,hasItems(44,76,54); --> it fails [no 4 in the collection]

        //loop through each of the element and make sure they are matching with Matchers inside the everyItem
        assertThat(numberList, everyItem(greaterThanOrEqualTo(1)));
        assertThat(numberList, everyItem(greaterThan(-5)));

        //relativeOrder --> // You don't have to put all elements !! the ORDER is matter !!
        assertThat(numberList, containsInRelativeOrder(3, 5, 1, 77, 44));
        assertThat(numberList, containsInRelativeOrder(3, 5, 1, 77, 44, 76));
        //assertThat(numberList,containsInRelativeOrder(76,3,5,1,77,44));// fails !!

        //AnyOrder --> Order doens't matter. All elements must be put !! Otherwise, it fails !!
        assertThat(numberList, containsInAnyOrder(76, 3, 5, 1, 77, 44));// doesn't fail !!
        //assertThat(numberList,containsInAnyOrder(3,5,1,77,44));// fails !!

    }
}
