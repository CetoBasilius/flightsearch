package com.basilio.flightsearch.dal.air;

import com.basilio.flightsearch.entities.AirportStub;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/13/12
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AirportListScraperTest {
    @Test
    public void testGetAirportStubList() {
        //TODO: test this method extensively
        AirportListScraper airportListScraper = new AirportListScraper();
        assertNotNull(airportListScraper.getAirportStubList());
    }

    @Test
    public void testGetAirportStubListLocal() {

        AirportListScraper airportListScraper = new AirportListScraper();
        assertNotNull(airportListScraper.getAirportStubListLocal());
        assertTrue(airportListScraper.getAirportStubListLocal().size()>0);
    }

    @Test
    public void testCreateAirportStubList(){
        AirportListScraper airportListScraper = new AirportListScraper();


        List<String> stringList = new ArrayList<String>();

        stringList.add("AAA - Some place, Country - Airport 1");
        stringList.add("BBB - Some place, Country - Airport 2");
        stringList.add("CCC - Some place, Country - Airport 3");
        stringList.add("DDD - Some place, Country - Airport 4");
        stringList.add("EEE - Some place, Country - Airport 5");
        stringList.add("FFF - Some place, Country - Airport 6");

        List<AirportStub> stubList = airportListScraper.createAirportStubList(stringList);

        assertEquals(6, stubList.size());

        assertEquals("AAA",stubList.get(0).getCode());
        assertEquals("FFF",stubList.get(5).getCode());
    }
}
