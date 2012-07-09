package com.basilio.flightsearch.pages;


import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;


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
    private ServiceDAO serviceDAO;

    @Property
    private double latin = 29.0958995819;

    @Property
    private double lngin = -111.047996521;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<Double> coordinatesParameter;

    public void setup(List<Double> coordinatesin){
        coordinatesParameter = coordinatesin;
        System.out.println(coordinatesParameter);
    }

}
