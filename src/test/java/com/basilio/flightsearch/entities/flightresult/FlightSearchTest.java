package com.basilio.flightsearch.entities.flightresult;

import com.basilio.flightsearch.entities.AirportStub;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/13/12
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlightSearchTest {
    @Test
    public void testGetDescription() throws Exception {
        FlightSearch flightSearch = new FlightSearch();
        flightSearch.setDirectFlight(false);
        flightSearch.setBudgetDollars(600);
        AirportStub departureAirport1 = new AirportStub("AAA", "descriptor");
        flightSearch.setDepartureAirport(departureAirport1);
        Date departureDate = new Date();
        flightSearch.setDepartureDate(departureDate);
        AirportStub destinationAirport1 = new AirportStub("BBB", "descriptor");
        flightSearch.setDestinationAirport(destinationAirport1);
        flightSearch.setNewBorns(0);
        flightSearch.setNumberAdults(2);
        flightSearch.setNumberChildren(1);
        Date returnDate = new Date();
        flightSearch.setReturnDate(returnDate);
        flightSearch.setRoundTrip(false);

        assertFalse(flightSearch.isDirectFlight());
        assertEquals(600,flightSearch.getBudgetDollars());
        assertEquals(departureAirport1,flightSearch.getDepartureAirport());
        assertEquals(departureDate,flightSearch.getDepartureDate());
        assertEquals(destinationAirport1,flightSearch.getDestinationAirport());
        assertEquals(0,flightSearch.getNewBorns());
        assertEquals(2,flightSearch.getNumberAdults());
        assertEquals(1,flightSearch.getNumberChildren());
        assertEquals(returnDate,flightSearch.getReturnDate());
        assertFalse(flightSearch.isRoundTrip());

        assertTrue(flightSearch.getDescription().contains("one-way"));
        assertFalse(flightSearch.getDescription().contains("round"));
        flightSearch.setRoundTrip(true);
        assertTrue(flightSearch.getDescription().contains("round"));
        assertFalse(flightSearch.getDescription().contains("one-way"));

        assertTrue(flightSearch.getDescription().contains(departureAirport1.getCode()));
        assertTrue(flightSearch.getDescription().contains(destinationAirport1.getCode()));

        assertTrue(flightSearch.getDescription().contains("segmented"));
        assertTrue(flightSearch.getDescription().contains("direct"));

        flightSearch.setDirectFlight(true);

        assertFalse(flightSearch.getDescription().contains("segmented"));
        assertTrue(flightSearch.getDescription().contains("direct"));

        assertTrue(flightSearch.getDescription().contains("2 adults"));
        flightSearch.setNumberAdults(1);
        assertTrue(flightSearch.getDescription().contains("1 adult"));

        assertTrue(flightSearch.getDescription().contains("1 child"));
        flightSearch.setNumberChildren(2);
        assertTrue(flightSearch.getDescription().contains("2 children"));

        assertFalse(flightSearch.getDescription().contains("newborn"));
        flightSearch.setNewBorns(1);
        assertTrue(flightSearch.getDescription().contains("newborn"));
        assertFalse(flightSearch.getDescription().contains("newborns"));
        flightSearch.setNewBorns(2);
        assertTrue(flightSearch.getDescription().contains("newborn"));
        assertTrue(flightSearch.getDescription().contains("newborns"));


    }
}
