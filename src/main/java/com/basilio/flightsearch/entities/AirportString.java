package com.basilio.flightsearch.entities;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/18/12
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class AirportString implements Comparable<AirportString>{

    private String string;

    private int occurrences;

    public AirportString(String s, int occurrences) {
        string = s;
        this.occurrences = occurrences;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public int compareTo(AirportString o) {
        return this.getOccurrences()-o.getOccurrences();
    }
}
