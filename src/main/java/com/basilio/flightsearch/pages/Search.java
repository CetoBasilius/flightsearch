package com.basilio.flightsearch.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Property;

/**
 *
 * @author Basilio
 *
 */
public class Search {
    @Inject
    private Session session;

    @Property
    private Date startDate;

    @Property
    private String originAirportName;

    //This assures this method gets called first
    @OnEvent(value = EventConstants.SUCCESS)
    void searchFlights()
    {

    }

    void onAction()
    {

    }

    List<String> onProvideCompletionsFromOriginAirportName(String partial)
    {
        //TODO consult a web service to grab airport names
        List<String> result = new ArrayList<String>();

        result.add("HMO");
        result.add("TUS");
        result.add("NYC");
        result.add("MEX");

        return result;
    }


}
