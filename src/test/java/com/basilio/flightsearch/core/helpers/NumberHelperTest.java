package com.basilio.flightsearch.core.helpers;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/31/12
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class NumberHelperTest {
    @Test
    public void testOrdinal() throws Exception {

        assertEquals("1st",NumberHelper.ordinal(1));
        assertEquals("2nd",NumberHelper.ordinal(2));
        assertEquals("3rd",NumberHelper.ordinal(3));
        assertEquals("13th",NumberHelper.ordinal(13));
        assertEquals("21st",NumberHelper.ordinal(21));
        assertEquals("31st",NumberHelper.ordinal(31));
        assertEquals("10th",NumberHelper.ordinal(10));
        assertEquals("1001st",NumberHelper.ordinal(1001));
        assertEquals("2114th",NumberHelper.ordinal(2114));
    }
}
