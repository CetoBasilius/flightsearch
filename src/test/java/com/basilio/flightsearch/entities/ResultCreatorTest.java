package com.basilio.flightsearch.entities;

import com.basilio.flightsearch.core.ResultCreator;
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
        ResultCreator resultCreator = new ResultCreator();
        assertNull(resultCreator.getGoodResult());
        resultCreator.setResultString("{}");
        assertNotNull(resultCreator.getGoodResult());
        assertEquals(FlightResult.class,resultCreator.getGoodResult().getClass());
    }
}
