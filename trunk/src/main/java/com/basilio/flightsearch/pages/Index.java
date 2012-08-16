package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/22/12
 * Time: 11:07 AM
 * <p/>
 * This page will never be visible. it should redirect to the search page if the user is logged in, or redirect to the about page if the user is a guest.
 */

@GuestAccess
public class Index {

    public Object onActivate() {
        return SearchPage.class;
    }
}
