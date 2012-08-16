package com.basilio.flightsearch.entities.flightresult;

import org.junit.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/31/12
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SegmentsTest {
    @Test
    public void getDescriptionsTest() {
        Segment segment = new Segment();

        createSegment(segment);

        assertNotNull(segment.getArrivalDescription());
        assertNotNull(segment.getDepartureDescription());
        assertNotNull(segment.getDurationDescription());

        assertNotNull(segment.getStopDescription());
        assertNotNull(segment.getDescription());

        assertEquals(String.class, segment.getArrivalDescription().getClass());
        assertEquals(String.class, segment.getDepartureDescription().getClass());
        assertEquals(String.class, segment.getDurationDescription().getClass());
    }

    @Test
    public void testGetHourDescriptions() {
        Segment segment = new Segment();
        createSegment(segment);

        Location arrival = new Location("Arrival");
        Location departure = new Location("Departure");

        arrival.setLocationDescription("This is an arrival description");
        departure.setLocationDescription("This is a departure description");

        arrival.setDate("");
        departure.setDate("");

        segment.setArrival(arrival);
        segment.setDeparture(departure);

        assertNotNull(segment.getDepartureHourDescription());
        assertNotNull(segment.getArrivalHourDescription());
    }

    @Test
    public void testGetStopDescription() {
        Segment segment = new Segment();
        createSegment(segment);

        ArrayList<Stopover> stopovers = new ArrayList<Stopover>();
        segment.setStopovers(stopovers);

        assertTrue(segment.getStopDescription().length() == 0);

        stopovers.add(new Stopover());

        assertTrue(segment.getStopDescription().length() > 0);
    }

    private void createSegment(Segment segment) {
        Location arrival = new Location("Arrival");
        Location departure = new Location("Departure");

        segment.setArrival(arrival);
        segment.setDeparture(departure);
        segment.setFlightNumber(new Integer(0));
        segment.setDuration("2:30");
        segment.setMarketingCabinTypeCode("MCTC");
        segment.setMarketingCabinTypeDescription("MCTD");
        segment.setMarketingCarrierCode("MCC");
        segment.setMarketingCarrierDescription("MCD");
        segment.setOperatingCarrierCode("OCC");
        segment.setOperatingCarrierDescription("OCD");
    }
}
