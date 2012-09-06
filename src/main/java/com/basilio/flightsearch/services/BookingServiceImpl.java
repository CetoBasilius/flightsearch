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
 * Time: 4:58 PM
 * This class should somehow connect with a booking server and attempt to book the selected flight.
 */
public class BookingServiceImpl implements BookingService{

    public boolean bookFlight(List<Passenger> passengers, ContactInformation contactInformation, Flight flight, CardDetails cardDetails) {
        return true;
    }
}
