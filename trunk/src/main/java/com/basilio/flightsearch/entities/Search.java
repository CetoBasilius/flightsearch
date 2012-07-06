package com.basilio.flightsearch.entities;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/4/12
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Search {

    private boolean isRoundTrip;

    private Date departureDate;
    private Date returnDate;

    private AirportStub departureAirport;
    private AirportStub destinationAirport;

    private int budgetDollars;

    private int numberAdults;
    private int numberChildren;

    private int newBorns;

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public AirportStub getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirportStub departureAirport) {
        this.departureAirport = departureAirport;
    }

    public int getNumberChildren() {
        return numberChildren;
    }

    public void setNumberChildren(int numberChildren) {
        this.numberChildren = numberChildren;
    }

    public int getNewBorns() {
        return newBorns;
    }

    public void setNewBorns(int newBorns) {
        this.newBorns = newBorns;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public AirportStub getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(AirportStub destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public int getBudgetDollars() {
        return budgetDollars;
    }

    public void setBudgetDollars(int budgetDollars) {
        this.budgetDollars = budgetDollars;
    }

    public int getNumberAdults() {
        return numberAdults;
    }

    public void setNumberAdults(int numberAdults) {
        this.numberAdults = numberAdults;
    }
}
