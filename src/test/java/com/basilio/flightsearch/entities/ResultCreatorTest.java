package com.basilio.flightsearch.entities;

import com.basilio.flightsearch.core.FlightResultCreator;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/27/12
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultCreatorTest {
    @Test
    public void testGetGoodResult() throws Exception {
        FlightResultCreator flightResultCreator = new FlightResultCreator();
        assertNull(flightResultCreator.getGoodResult());
        flightResultCreator.setResultString("{}");
        assertNotNull(flightResultCreator.getGoodResult());
        assertEquals(FlightResult.class, flightResultCreator.getGoodResult().getClass());
    }
}
