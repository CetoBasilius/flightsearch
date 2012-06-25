package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/25/12
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexTest {

    @Test
    public void AboutShouldHaveGuestAccess(){
        assertTrue(About.class.isAnnotationPresent(GuestAccess.class));
    }
}
