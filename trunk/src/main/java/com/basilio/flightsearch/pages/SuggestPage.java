package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.flightresult.Flight;
import org.apache.tapestry5.annotations.Property;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/26/12
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
@GuestAccess
public class SuggestPage {

    @Property
    private Flight flight;

    public void setup(Flight flight) {
        this.flight = flight;
    }
}
