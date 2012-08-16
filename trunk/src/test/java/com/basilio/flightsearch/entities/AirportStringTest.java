package com.basilio.flightsearch.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/18/12
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class AirportStringTest {

    @Test
    public void testCompareTo() throws Exception {
        AirportString airportString1 = new AirportString("a string", 2);
        AirportString airportString2 = new AirportString("a string", 2);
        AirportString airportString3 = new AirportString("a string", 4);
        AirportString airportString4 = new AirportString("a string", 1);

        List<AirportString> airportStringList = new ArrayList<AirportString>();
        airportStringList.add(airportString1);
        airportStringList.add(airportString2);
        airportStringList.add(airportString3);
        airportStringList.add(airportString4);

        assertEquals(2, airportStringList.get(0).getOccurrences());
        assertEquals(2, airportStringList.get(1).getOccurrences());
        assertEquals(4, airportStringList.get(2).getOccurrences());
        assertEquals(1, airportStringList.get(3).getOccurrences());

        Collections.sort(airportStringList);

        assertEquals(1, airportStringList.get(0).getOccurrences());
        assertEquals(2, airportStringList.get(1).getOccurrences());
        assertEquals(2, airportStringList.get(2).getOccurrences());
        assertEquals(4, airportStringList.get(3).getOccurrences());

        Collections.sort(airportStringList, Collections.reverseOrder());

        assertEquals(4, airportStringList.get(0).getOccurrences());
        assertEquals(2, airportStringList.get(1).getOccurrences());
        assertEquals(2, airportStringList.get(2).getOccurrences());
        assertEquals(1, airportStringList.get(3).getOccurrences());
    }
}
