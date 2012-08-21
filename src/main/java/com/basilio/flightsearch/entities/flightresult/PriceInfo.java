package com.basilio.flightsearch.entities.flightresult;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:31 PM
 * Overall price information
 */

public class PriceInfo {
    private Adults adults;
    private Children children;
    private Infants infants;
    private Total total;

    public Adults getAdults() {
        return this.adults;
    }

    public void setAdults(Adults adults) {
        this.adults = adults;
    }

    public Children getChildren() {
        return this.children;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public Infants getInfants() {
        return this.infants;
    }

    public void setInfants(Infants infants) {
        this.infants = infants;
    }

    public Total getTotal() {
        return this.total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }
}
