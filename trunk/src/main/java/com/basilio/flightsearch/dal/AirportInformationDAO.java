package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.AirportStub;
import org.w3c.dom.Document;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/2/12
 * Time: 3:58 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AirportInformationDAO {

    public AirportStub getAirportData(String airportCode);
}
