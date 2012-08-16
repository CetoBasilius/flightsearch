package com.basilio.flightsearch.entities.flightresult;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/10/12
 * Time: 9:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class StopoverTest {

    @Test
    public void testStopovers() {
        Stopover stopover = new Stopover();
        String code = "code";
        stopover.setAirportCode(code);
        String description = "description";
        stopover.setAirportDescription(description);
        String duration = "duration";
        stopover.setDuration(duration);

        assertEquals(code, stopover.getAirportCode());
        assertEquals(description, stopover.getAirportDescription());
        assertEquals(duration, stopover.getDuration());
    }
}
