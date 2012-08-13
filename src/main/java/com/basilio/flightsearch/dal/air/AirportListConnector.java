package com.basilio.flightsearch.dal.air;

import com.basilio.flightsearch.entities.AirportStub;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/5/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AirportListConnector {

    /**
     * Fetches a list with AirportStubs, which contains only a small description and the 3 letter IATA Code for each airport.
     * @return An airportStub list
     * @throws IOException If there was a problem getting the list
     */
    public List<AirportStub> getAirportStubList() throws IOException;

    /**
     * Fetches a list of demo airports created on the fly.
     * @return an incomplete list or demo list of AirportStubs.
     */
    public List<AirportStub> getAirportStubListLocal();

}
