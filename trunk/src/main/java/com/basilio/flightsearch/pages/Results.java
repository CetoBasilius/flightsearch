package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.Airport;
import com.basilio.flightsearch.entities.AirportStub;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/26/12
 * Time: 8:09 PM
 * The user will be able to see the search results here.
 * Search results include Origin, Destination, Price, Distance, Estimated Time.
 */

@GuestAccess
public class Results {


    @Property
    @Persist(PersistenceConstants.FLASH)
    private String origin;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String destination;

    public void setup(String origin,String destination)
    {
        this.origin = origin;
        this.destination = destination;
    }

}
