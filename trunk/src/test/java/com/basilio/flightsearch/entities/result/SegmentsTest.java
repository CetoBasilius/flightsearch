package com.basilio.flightsearch.entities.result;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/31/12
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SegmentsTest {
    @Test
    public void getDescriptionsTest(){
        Segment segment = new Segment();

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

        assertNotNull(segment.getArrivalDescription());
        assertNotNull(segment.getDepartureDescription());
        assertNotNull(segment.getDurationDescription());

        assertEquals(String.class,segment.getArrivalDescription().getClass());
        assertEquals(String.class,segment.getDepartureDescription().getClass());
        assertEquals(String.class,segment.getDurationDescription().getClass());

    }
}
