package com.basilio.flightsearch.entities.result;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/24/12
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RouteTest {
    @Test
    public void testGetSegmentsDescription() throws Exception {
        OutboundRoute outboundRoutes = new OutboundRoute();

        List<Segment> insertedList = new ArrayList<Segment>();

        Segment segment1 = new Segment();
        segment1.setArrival(new Arrival("BBB"));
        segment1.setDeparture(new Departure("AAA"));

        Segment segment2 = new Segment();
        segment2.setArrival(new Arrival("CCC"));
        segment2.setDeparture(new Departure("BBB"));

        insertedList.add(segment1);
        insertedList.add(segment2);

        outboundRoutes.setSegments(insertedList);

        assertEquals(2, outboundRoutes.getSegments().size());

        assertEquals(3,outboundRoutes.getSegmentsDescription().length);
        assertEquals("AAA",outboundRoutes.getSegmentsDescription()[0]);
        assertEquals("BBB",outboundRoutes.getSegmentsDescription()[1]);
        assertEquals("CCC",outboundRoutes.getSegmentsDescription()[2]);


    }
}
