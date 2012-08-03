package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.flightresult.FlightSearch;
import com.basilio.flightsearch.entities.flightresult.FlightResult;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/5/12
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FlightSearchConnector {

    /**
     * Will consult a web service for a list of flights with the given search.
     * @param flightSearch
     * @return
     */
    public FlightResult searchFlights(FlightSearch flightSearch);


}
