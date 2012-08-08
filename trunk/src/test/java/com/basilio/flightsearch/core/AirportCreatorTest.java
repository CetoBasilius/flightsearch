package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.airport.Airport;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.hotelresult.HotelResult;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/27/12
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class AirportCreatorTest {
    @Test
    public void testGetGoodResult() throws Exception {
        AirportCreator airportCreator = new AirportCreator();
        assertNull(airportCreator.getGoodAirport());
        airportCreator.setAirportString("{}");
        assertNotNull(airportCreator.getGoodAirport());
        assertEquals(Airport.class, airportCreator.getGoodAirport().getClass());
        assertEquals(airportCreator.getAirportString(),airportCreator.getAllResultString());
    }
}
