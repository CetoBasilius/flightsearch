package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.hotelresult.HotelResult;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/27/12
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelResultCreatorTest {
    @Test
    public void testGetGoodResult() throws Exception {
        HotelResultCreator hotelResultCreator = new HotelResultCreator();
        assertNull(hotelResultCreator.getGoodHotelResult());
        hotelResultCreator.setResultString("{}");
        assertNotNull(hotelResultCreator.getGoodHotelResult());
        assertEquals(HotelResult.class, hotelResultCreator.getGoodHotelResult().getClass());
        assertEquals(hotelResultCreator.getResultString(), hotelResultCreator.getAllResultString());
    }
}
