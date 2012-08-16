package com.basilio.flightsearch.core.helpers;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/30/12
 * Time: 7:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class NumberHelper {
    public static String ordinal(int i) {
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }
}
