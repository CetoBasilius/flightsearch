package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.AirportInformationDAO;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.Airport;
import com.basilio.flightsearch.entities.AirportStub;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Basilio
 */

@GuestAccess
public class Search {

    @Inject
    ServiceDAO serviceDAO;

    @Inject
    AirportInformationDAO airportInformationDAO;

    @Property
    private Form searchForm;

    @Property
    private Date startDate;

    @Property
    private Date endDate;

    @Property
    private String origin;

    @Property
    private String destination;

    @Property
    @Persist
    private List<AirportStub> allAirportStubs;

    @InjectPage
    private Results results;

    @Log
    @OnEvent(value = EventConstants.SUCCESS, component = "SearchForm")
    Object startSearch() {
        String codeOrigin = airportInformationDAO.getAirportData(origin.substring(1,4));
        String codeDestination = airportInformationDAO.getAirportData(destination.substring(1,4));
        if(codeOrigin==null || codeDestination==null){
            return null;
        }

        results.setup(codeOrigin,codeDestination);
        return results;
    }

    @Log
    @OnEvent(value = EventConstants.ACTIVATE)
    public void getAirports() {
        if (allAirportStubs == null) {
            allAirportStubs = serviceDAO.findWithNamedQuery(AirportStub.ALL);
        }
    }

    public List<AirportStub> getAirportStublist() {
        List<AirportStub> airportList = serviceDAO.findWithNamedQuery(AirportStub.ALL);
        return airportList;
    }

    List<String> onProvideCompletionsFromOrigin(String partial) {

        return getAutoCompleteList(partial);
    }

    List<String> onProvideCompletionsFromDestination(String partial) {
        return getAutoCompleteList(partial);
    }

    List<String> getAutoCompleteList(String partial) {
        List<String> result = new ArrayList<String>();

        for (AirportStub airport : allAirportStubs) {
            int index1 = airport.toString().toLowerCase().indexOf(partial.toLowerCase());
            if (index1 != -1) {
                result.add(airport.toString());
            }
        }

        return result;
    }

}
