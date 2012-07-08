package com.basilio.flightsearch.pages;


import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.AirportInformationDAO;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;


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
    private Authenticator authenticator;

    @Inject
    private AirportInformationDAO airportInformationDAO;

    @Inject
    private ServiceDAO serviceDAO;

}
