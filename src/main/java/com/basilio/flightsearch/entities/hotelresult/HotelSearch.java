package com.basilio.flightsearch.entities.hotelresult;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/3/12
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelSearch {
    String cityCode;
    private int numberAdults;
    private int numberChildren;
    private int newBorns;

    private Date checkOutDate;
    private Date checkInDate;
    private String distribution;

    public HotelSearch() {

    }

    public HotelSearch(String city, Date checkIn, Date checkOut) {

        this.cityCode = city;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getNumberAdults() {
        return numberAdults;
    }

    public void setNumberAdults(int numberAdults) {
        this.numberAdults = numberAdults;
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

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

}
