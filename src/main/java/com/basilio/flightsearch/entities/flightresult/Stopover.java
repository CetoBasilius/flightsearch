package com.basilio.flightsearch.entities.flightresult;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/26/12
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Stopover {

    private String duration;
    private String airportCode;
    private String airportDescription;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportDescription() {
        return airportDescription;
    }

    public void setAirportDescription(String airportDescription) {
        this.airportDescription = airportDescription;
    }
}
