package com.basilio.flightsearch.entities;

import java.text.SimpleDateFormat;
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
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");


    private boolean isDirectFlight;

    public boolean isDirectFlight() {
        return isDirectFlight;
    }

    public void setDirectFlight(boolean directFlight) {
        isDirectFlight = directFlight;
    }

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

    public String getDescription() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("You searched for a ");
        if(this.isRoundTrip()){
            buffer.append(" round");
        }else{
            buffer.append(" one-way");
        }
        buffer.append(" flight from ");
        buffer.append(this.getDepartureAirport().getCode());
        buffer.append(" to ");
        buffer.append(this.getDestinationAirport().getCode());
        buffer.append(", ");
        if(this.isRoundTrip()){
            buffer.append("from ");
            buffer.append(sdf.format(this.getDepartureDate()).toString());
            buffer.append(" to ");
            buffer.append(sdf.format(this.getReturnDate()).toString());
        }else{
            buffer.append("on ");
            buffer.append(sdf.format(this.getDepartureDate()).toString());
            buffer.append(", ");
        }
        if(this.isDirectFlight()){
            buffer.append(" including only direct flights, ");
        }else{
            buffer.append(" including segmented and direct flights, ");
        }

        if(this.getNumberAdults()>0){
            buffer.append("for ");
            buffer.append(this.getNumberAdults());
            if(this.getNumberAdults()>1){
                buffer.append(" adults, ");
            }else{
                buffer.append(" adult, ");
            }
        }
        if(this.getNumberChildren()>0){
            buffer.append("for ");
            buffer.append(this.getNumberChildren());
            if(this.getNumberChildren()>1){
                buffer.append(" children, ");
            }else{
                buffer.append(" child, ");
            }
        }
        if(this.getNewBorns()>0){
            buffer.append("for ");
            buffer.append(this.getNewBorns());
            if(this.getNewBorns()>1){
                buffer.append(" newborns, ");
            }else{
                buffer.append(" newborn, ");
            }
        }
        buffer.append("with a budget of no more than ");
        buffer.append(this.getBudgetDollars());
        buffer.append(" dollars.");
        return buffer.toString();
    }
}
