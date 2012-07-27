package com.basilio.flightsearch.entities;

import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/27/12
 * Time: 1:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AirportStubTest {

    @Test
    public void testToString(){
        AirportStub airportStub1 = new AirportStub();
        airportStub1.setCode("AAA");
        airportStub1.setDescriptor("descriptor");

        assertEquals("(AAA) descriptor",airportStub1.toString());

    }

    @Test
    public void testAirportStub(){
        AirportStub airportStub2 = new AirportStub("BBB","descriptor2");
        assertEquals("(BBB) descriptor2",airportStub2.toString());
    }
}
