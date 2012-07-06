package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.Result;
import com.basilio.flightsearch.entities.Search;

import java.util.List;

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
     * @param search
     * @return
     */
    public List<Result> searchOneWayFlights(Search search);

    /**
     * Will search for the most economic flight
     * @param search
     * @return
     */
    public Result searchMostEconomicFlight(Search search);

    /**
     * Will search for the most fit flight, adjusting to your budget, but not the cheapest flight.
     * @param search
     * @return
     */
    public Result searchMostFitFlight(Search search);

}
