package com.basilio.flightsearch.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/18/12
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchPageTest {

    @Test
    public void testToggleRoundTrip() {
        SearchPage searchPage = new SearchPage();

        assertFalse(searchPage.showRoundTripValue());

        searchPage.toggleRoundTrip();

        assertTrue(searchPage.showRoundTripValue());

        searchPage.toggleRoundTrip();

        assertFalse(searchPage.showRoundTripValue());

    }
}
