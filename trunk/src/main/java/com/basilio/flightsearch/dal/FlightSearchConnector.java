package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.Result;

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
     * @param economic will adjust to the specified budget in search object
     * @return
     */
    public Result searchOneWayFlights(Search search, boolean economic);

    /**
     * Will consult a web service for a list of flights with the given search.
     * @param search
     * @param economic will adjust to the specified budget in search object
     * @return
     */
    public Result searchRoundFlights(Search search, boolean economic);

}
