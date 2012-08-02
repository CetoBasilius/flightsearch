package com.basilio.flightsearch.core;


import com.basilio.flightsearch.entities.flightresult.Flight;
import com.basilio.flightsearch.entities.flightresult.Route;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.flightresult.Segment;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/31/12
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultFilterImplTest {
    @Test
    public void testFilterSearch() throws Exception {
        FlightResultFilter filterFlight = new FlightResultFilterImpl();

        FlightResult flightResult = new FlightResult();

        Flight directFlight = new Flight();
        List<Route> outRoutes1 = new ArrayList<Route>();
        Route outboundRoute1 = new Route();
        List<Segment> segments1 = new ArrayList<Segment>();
        Segment segment1 = new Segment();

        segments1.add(segment1);
        segments1.add(segment1);
        outboundRoute1.setSegments(segments1);
        outRoutes1.add(outboundRoute1);
        directFlight.setOutboundRoutes(outRoutes1);

        assertEquals(1,directFlight.getOutboundRoutes().size());
        assertEquals(2,directFlight.getOutboundRoutes().get(0).getSegments().size());


        Flight segmentedFlight = new Flight();
        List<Route> outRoutes2 = new ArrayList<Route>();
        Route outboundRoute2 = new Route();
        List<Segment> segments2 = new ArrayList<Segment>();
        Segment segment2 = new Segment();

        segments2.add(segment2);
        segments2.add(segment2);
        segments2.add(segment2);
        outboundRoute2.setSegments(segments2);
        outRoutes2.add(outboundRoute2);
        segmentedFlight.setOutboundRoutes(outRoutes2);

        assertEquals(1,segmentedFlight.getOutboundRoutes().size());
        assertEquals(3,segmentedFlight.getOutboundRoutes().get(0).getSegments().size());

        List<Flight> flights = new ArrayList<Flight>();
        flights.add(directFlight);
        flights.add(segmentedFlight);

        flightResult.setFlights(flights);

        assertEquals(2, flightResult.getFlights().size());


    }
}
