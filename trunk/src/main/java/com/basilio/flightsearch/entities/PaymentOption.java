package com.basilio.flightsearch.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/27/12
 * Time: 11:15 AM
 * One of the options the user will be given to make a booking.
 */
public class PaymentOption {

    private String description;
    private List<String> cards;

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public int getNumberOfPayments() {
        return numberOfPayments;
    }

    public void setNumberOfPayments(int numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
    }

    public int getFirstPayment() {
        return firstPayment;
    }

    public void setFirstPayment(int firstPayment) {
        this.firstPayment = firstPayment;
    }

    public int getOtherPayments() {
        return otherPayments;
    }

    public void setOtherPayments(int otherPayments) {
        this.otherPayments = otherPayments;
    }

    private int numberOfPayments;

    private int firstPayment;
    private int otherPayments;

    public PaymentOption(){
        cards = new ArrayList<String>();
    }

    public String getDescription() {
        String paymentString = "Payment";
        if(numberOfPayments>1){paymentString+="s";}
        return numberOfPayments+" "+paymentString;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardsString() {
        return cards.toString();
    }
}
