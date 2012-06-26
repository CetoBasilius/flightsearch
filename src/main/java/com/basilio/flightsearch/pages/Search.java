package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.Airport;
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
    ServiceDAO serviceDAO;

    @Property
    private Date startDate;

    @Property
    private Date endDate;

    @Property
    private String originAirportName;

    @Property
    private String destinationAirportName;

    @Property
    @Persist
    private List<Airport> allAirports;

    void onAction()
    {

    }

    @Log
    @OnEvent(value = EventConstants.ACTIVATE)
    public void getAirports()
    {
        if(allAirports == null){
            allAirports = serviceDAO.findWithNamedQuery(Airport.ALL);
        }
    }

    public List<Airport> getAirportlist()
    {
        List<Airport> airportList = serviceDAO.findWithNamedQuery(Airport.ALL);
        return airportList;
    }

    List<String> onProvideCompletionsFromOriginAirportName(String partial)
    {

        return getAutoCompleteList(partial);
    }

    List<String> onProvideCompletionsFromDestinationAirportName(String partial)
    {
        return getAutoCompleteList(partial);
    }

    List<String> getAutoCompleteList(String partial) {
        List<String> result = new ArrayList<String>();

        for(Airport airport : allAirports){
            int index1 = airport.toString().toLowerCase().indexOf(partial.toLowerCase());
            if (index1 != -1)
            {
                result.add(airport.toString());
            }
        }

        return result;
    }

}
