package com.basilio.flightsearch.core.helpers;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/1/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateHelper {
    public static String getHoursMinutes(Date date){
        int milliseconds = (int)date.getTime();
        int seconds = milliseconds/1000;
        int totalMinutes = seconds/60;
        int hours = totalMinutes/60;
        int minutes = totalMinutes%60;

        String hourString = "";
        if(hours!=1){
            hourString = " hours, ";
        } else {
            hourString = " hour, ";
        }

        String minutesString = "";
        if(minutes!=1){
            minutesString = " minutes";
        } else {
            minutesString = " minute";
        }

        return hours+hourString+minutes+minutesString;
    }
}
