package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.flightresult.FlightResult;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/31/12
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public interface FlightResultFilter {

    FlightResult filterSearch(FlightResult inFlightResult, int budget, int segments);

    String getDescription();

    boolean wasResultFiltered();
    void setWasResultFiltered(boolean option);
}
