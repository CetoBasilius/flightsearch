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
    private String name;
    private String lastName;
    private String Gender;
    private String birthDay;
    private String birthMonth;
    private String birthYear;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

}
