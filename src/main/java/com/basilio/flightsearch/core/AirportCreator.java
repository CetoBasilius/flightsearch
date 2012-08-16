package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.airport.Airport;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/30/12
 * Time: 4:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class AirportCreator {

    private static final Logger logger = LoggerFactory.getLogger(AirportCreator.class);
    private String airportString;
    private Gson gson;

    public AirportCreator() {
        gson = new Gson();
    }

    public String getAirportString() {
        return airportString;
    }

    public Airport getGoodAirport() {
        Airport goodAirport = null;
        try {
            goodAirport = gson.fromJson(getAllResultString(), Airport.class);
        } catch (Exception e) {
            logger.error("Could not create good airport object.");
        }
        return goodAirport;
    }

    public String getAllResultString() {
        return airportString;
    }

    public void setAirportString(String airportString) {
        this.airportString = airportString;
    }

}
