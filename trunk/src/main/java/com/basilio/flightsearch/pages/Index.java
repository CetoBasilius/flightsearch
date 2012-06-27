package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/22/12
 * Time: 11:07 AM
 *
 * This page will never be visible. it should redirect to the search page if the user is logged in, or redirect to the about page if the user is a guest.
 *
 */

@GuestAccess
public class Index {
    @Inject
    private Authenticator authenticator;

    public Object onActivate() {
        return authenticator.isLoggedIn() ? Search.class : About.class;
    }

}
