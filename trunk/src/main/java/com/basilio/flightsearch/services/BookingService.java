package com.basilio.flightsearch.services;

import com.basilio.flightsearch.entities.CardDetails;
import com.basilio.flightsearch.entities.ContactInformation;
import com.basilio.flightsearch.entities.Passenger;
import com.basilio.flightsearch.entities.flightresult.Flight;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 9/5/12
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BookingService{

    public boolean bookFlight(List<Passenger> passengers, ContactInformation contactInformation, Flight flight, CardDetails cardDetails);
}
