package com.basilio.flightsearch.entities;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/20/12
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Passenger {

    public static final int TYPE_ADULT = 0;
    public static final int TYPE_CHILD = 1;
    public static final int TYPE_INFANT = 2;

    private int passengerType;

    public Passenger(int type){
        passengerType = type;
    }

    public int getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(int passengerType) {
        this.passengerType = passengerType;
    }

    public String getPassengerTypeString() {
        switch (this.getPassengerType()){
            case TYPE_ADULT : {
                return "Adult";
            }
            case TYPE_CHILD : {
                return "Child";
            }
            case TYPE_INFANT : {
                return "Infant";
            }
        }
        return "Unknown";
    }
}
