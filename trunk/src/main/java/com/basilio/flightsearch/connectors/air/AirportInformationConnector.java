package com.basilio.flightsearch.connectors.air;

import com.basilio.flightsearch.entities.AirportStub;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/2/12
 * Time: 3:58 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AirportInformationConnector {

    /**
     * Will return a new object that now contains latitude and longitude.
     */
    public AirportStub getAirportData(AirportStub airportIn);

    /**
     * Will return new objects that now contain latitude and longitude.
     */
    public List<AirportStub> getAirportData(List<AirportStub> airportIn);
}
