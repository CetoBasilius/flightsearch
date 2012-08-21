package com.basilio.flightsearch.entities;

import com.basilio.flightsearch.entities.Passenger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/20/12
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class PassengerTest {
    @Test
    public void testSetPassengerType() {
        Passenger passenger = new Passenger(Passenger.TYPE_ADULT);

        assertEquals(Passenger.TYPE_ADULT,passenger.getPassengerType());
        passenger.setPassengerType(Passenger.TYPE_CHILD);
        assertEquals(Passenger.TYPE_CHILD,passenger.getPassengerType());

    }

    @Test
    public void testGetPassengerTypeString(){
        Passenger passenger = new Passenger(Passenger.TYPE_ADULT);

        assertEquals("Adult",passenger.getPassengerTypeString());
        passenger.setPassengerType(Passenger.TYPE_CHILD);
        assertEquals("Child", passenger.getPassengerTypeString());
        passenger.setPassengerType(Passenger.TYPE_INFANT);
        assertEquals("Infant",passenger.getPassengerTypeString());

        passenger.setPassengerType(899898);
        assertEquals("Unknown",passenger.getPassengerTypeString());
    }
}
