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
    private Date endDate;

    @Property
    private String originAirportName;

    @Property
    private String destinationAirportName;

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
        List<String> allAirports = new ArrayList<String>();

        allAirports.add("HMO Hermosillo Sonora Mexico");
        allAirports.add("TUS Tucson Arizona USA");
        allAirports.add("NYC New York City USA");
        allAirports.add("MEX Mexico DF Mexico");
        allAirports.add("LAX Los Angeles California USA");
        allAirports.add("SFO San Francisco California USA");
        allAirports.add("XEX Paris France");
        allAirports.add("BER Berlin Germany");
        allAirports.add("PGG Sao Paulo Brazil");



        List<String> result = new ArrayList<String>();

        for(String airport : allAirports){
            int index1 = airport.toLowerCase().indexOf(partial.toLowerCase());
            if (index1 != -1)
            {
                result.add(airport);
            }
        }

        return result;
    }

    List<String> onProvideCompletionsFromDestinationAirportName(String partial)
    {
        //TODO consult a web service to grab airport names
        List<String> allAirports = new ArrayList<String>();

        allAirports.add("HMO Hermosillo Sonora Mexico");
        allAirports.add("TUS Tucson Arizona USA");
        allAirports.add("NYC New York City USA");
        allAirports.add("MEX Mexico DF Mexico");
        allAirports.add("LAX Los Angeles California USA");
        allAirports.add("SFO San Francisco California USA");
        allAirports.add("XEX Paris France");
        allAirports.add("BER Berlin Germany");
        allAirports.add("PGG Sao Paulo Brazil");

        List<String> result = new ArrayList<String>();

        for(String airport : allAirports){
            int index1 = airport.toLowerCase().indexOf(partial.toLowerCase());
            if (index1 != -1)
            {
                result.add(airport);
            }
        }

        return result;
    }


}
