package com.basilio.flightsearch.entities.flightresult;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        Route outboundRoutes = new Route();

        List<Segment> insertedList = new ArrayList<Segment>();

        Segment segment1 = new Segment();
        segment1.setArrival(new Location("BBB"));
        segment1.setDeparture(new Location("AAA"));

        Segment segment2 = new Segment();
        segment2.setArrival(new Location("CCC"));
        segment2.setDeparture(new Location("BBB"));

        insertedList.add(segment1);
        insertedList.add(segment2);

        outboundRoutes.setSegments(insertedList);

        assertEquals(2, outboundRoutes.getSegments().size());

        assertEquals(3,outboundRoutes.getSegmentsDescription().length);
        assertEquals("AAA",outboundRoutes.getSegmentsDescription()[0]);
        assertEquals("BBB",outboundRoutes.getSegmentsDescription()[1]);
        assertEquals("CCC",outboundRoutes.getSegmentsDescription()[2]);


    }

    @Test
    public void testGetSegmentNumberString(){
        Route route = new Route();
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment());
        route.setSegments(segments);

        assertEquals(segments,route.getSegments());

        assertTrue(route.getSegmentsNumber().contains("Direct"));
        segments.add(new Segment());
        assertTrue(route.getSegmentsNumber().contains("2"));
        segments.add(new Segment());
        assertTrue(route.getSegmentsNumber().contains("3"));
        segments.add(new Segment());
        assertTrue(route.getSegmentsNumber().contains("4"));

    }

    @Test
    public void testGetScheduleDescription(){
        Route route = new Route();
        List<Segment> segments = new ArrayList<Segment>();
        Segment testSegment = new Segment();
        testSegment.setArrival(new Location("Arrival"));
        testSegment.setDeparture(new Location("Departure"));
        segments.add(testSegment);

        route.setSegments(segments);

        route.getSegments().get(0).getDeparture().setDate("2012-12-21T16:16:16");
        route.setDuration("2:30");
        assertEquals("2:30",route.getDuration());

        assertTrue(route.getScheduleDescription().contains("Dec"));
        assertTrue(route.getScheduleDescription().contains("21"));
        assertTrue(route.getScheduleDescription().contains("16"));

        assertTrue(route.getDurationDescription().contains("2 hours"));
        assertTrue(route.getDurationDescription().contains("30 minutes"));
    }

    @Test
    public void testOtherMethods(){
        Route route = new Route();

        route.setType("test type");
        assertEquals("test type",route.getType());

        route.setHasAirportChange(true);
        assertTrue(route.getHasAirportChange());
    }

    @Test
    public void testGetWaitDescription(){
        Route route = new Route();
        List<Segment> segments = new ArrayList<Segment>();
        Segment testSegment1 = new Segment();
        testSegment1.setArrival(new Location("Arrival"));
        testSegment1.setDeparture(new Location("Departure"));
        Segment testSegment2 = new Segment();
        testSegment2.setArrival(new Location("Arrival"));
        testSegment2.setDeparture(new Location("Departure"));

        segments.add(testSegment1);
        segments.add(testSegment2);

        route.getWaitDescription(0,1);
    }
}
