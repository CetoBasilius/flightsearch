package com.basilio.flightsearch.core.helpers;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/8/12
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class DateHelperTest {
    @Test
    public void testGetHoursMinutes() {
        Date date = new Date();

        date.setTime(60000);
        assertEquals("0 hours, 1 minute", DateHelper.getHoursMinutes(date));

        date.setTime(120000);
        assertEquals("0 hours, 2 minutes", DateHelper.getHoursMinutes(date));

        date.setTime(3600000);
        assertEquals("1 hour, 0 minutes", DateHelper.getHoursMinutes(date));

        date.setTime(3660000);
        assertEquals("1 hour, 1 minute", DateHelper.getHoursMinutes(date));

        date.setTime(3720000);
        assertEquals("1 hour, 2 minutes", DateHelper.getHoursMinutes(date));

        date.setTime(7200000);
        assertEquals("2 hours, 0 minutes", DateHelper.getHoursMinutes(date));

        date.setTime(7260000);
        assertEquals("2 hours, 1 minute", DateHelper.getHoursMinutes(date));

        date.setTime(7360000);
        assertEquals("2 hours, 2 minutes", DateHelper.getHoursMinutes(date));

    }
}
