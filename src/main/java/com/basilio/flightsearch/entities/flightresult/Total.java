package com.basilio.flightsearch.entities.flightresult;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:56 PM
 * Total price of search result
 */

public class Total {
    private Number charges;
    private Number fare;
    private Number taxes;

    public Number getCharges() {
        return this.charges;
    }

    public void setCharges(Number charges) {
        this.charges = charges;
    }

    public Number getFare() {
        return this.fare;
    }

    public void setFare(Number fare) {
        this.fare = fare;
    }

    public Number getTaxes() {
        return this.taxes;
    }

    public void setTaxes(Number taxes) {
        this.taxes = taxes;
    }
}
