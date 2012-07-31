package com.basilio.flightsearch.core;


import com.basilio.flightsearch.entities.result.Flights;
import com.basilio.flightsearch.entities.result.OutboundRoutes;
import com.basilio.flightsearch.entities.result.Result;
import com.basilio.flightsearch.entities.result.Segments;
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

        Flights directFlight = new Flights();
        List<OutboundRoutes> outRoutes1 = new ArrayList<OutboundRoutes>();
        OutboundRoutes outboundRoute1 = new OutboundRoutes();
        List<Segments> segments1 = new ArrayList<Segments>();
        Segments segment1 = new Segments();

        segments1.add(segment1);
        segments1.add(segment1);
        outboundRoute1.setSegments(segments1);
        outRoutes1.add(outboundRoute1);
        directFlight.setOutboundRoutes(outRoutes1);

        assertEquals(1,directFlight.getOutboundRoutes().size());
        assertEquals(2,directFlight.getOutboundRoutes().get(0).getSegments().size());


        Flights segmentedFlight = new Flights();
        List<OutboundRoutes> outRoutes2 = new ArrayList<OutboundRoutes>();
        OutboundRoutes outboundRoute2 = new OutboundRoutes();
        List<Segments> segments2 = new ArrayList<Segments>();
        Segments segment2 = new Segments();

        segments2.add(segment2);
        segments2.add(segment2);
        segments2.add(segment2);
        outboundRoute2.setSegments(segments2);
        outRoutes2.add(outboundRoute2);
        segmentedFlight.setOutboundRoutes(outRoutes2);

        assertEquals(1,segmentedFlight.getOutboundRoutes().size());
        assertEquals(3,segmentedFlight.getOutboundRoutes().get(0).getSegments().size());

        List<Flights> flights = new ArrayList<Flights>();
        flights.add(directFlight);
        flights.add(segmentedFlight);

        result.setFlights(flights);

        assertEquals(2,result.getFlights().size());


    }
}
