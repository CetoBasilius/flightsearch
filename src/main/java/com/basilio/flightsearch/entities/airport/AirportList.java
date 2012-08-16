package com.basilio.flightsearch.entities.airport;

import java.util.List;

public class AirportList {
    private List<Airport> airports;
    private Meta meta;

    public List<Airport> getAirports() {
        return this.airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
