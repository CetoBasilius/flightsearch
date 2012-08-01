package com.basilio.flightsearch.core;


import com.basilio.flightsearch.entities.result.Flight;
import com.basilio.flightsearch.entities.result.OutboundRoute;
import com.basilio.flightsearch.entities.result.Result;
import com.basilio.flightsearch.entities.result.Segment;
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
        ResultFilter filter = new ResultFilterImpl();

        Result result = new Result();

        Flight directFlight = new Flight();
        List<OutboundRoute> outRoutes1 = new ArrayList<OutboundRoute>();
        OutboundRoute outboundRoute1 = new OutboundRoute();
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
        List<OutboundRoute> outRoutes2 = new ArrayList<OutboundRoute>();
        OutboundRoute outboundRoute2 = new OutboundRoute();
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

        result.setFlights(flights);

        assertEquals(2,result.getFlights().size());


    }
}
