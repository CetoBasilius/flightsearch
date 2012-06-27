package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/26/12
 * Time: 4:48 AM
 * This is just a test page. Future enhancements are tested here.
 */

@GuestAccess
public class Tester {
    @Inject
    Authenticator authenticator;

    @Inject
    ServiceDAO serviceDAO;

    @Log
    public void onActionFromTestHTTP() {

    }
}
