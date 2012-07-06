package com.basilio.flightsearch.dal;

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
public interface HttpListScraper {

    public List<AirportStub> loadAirportStubsWithHTTP() throws IOException;

    public List<AirportStub> createDemoAirportStubsLocal();

}
