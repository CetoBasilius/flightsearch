package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.AirportInformationDAO;
import com.basilio.flightsearch.dal.FlightSearchConnector;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.Result;
import com.basilio.flightsearch.entities.Search;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Basilio
 */

@GuestAccess
public class SearchPage {

    @Inject
    private FlightSearchConnector flightSearchConnector;

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    private AirportInformationDAO airportInformationDAO;

    //----------------- SearchPage form ----------------
    @Component
    private Form searchForm;

    @Inject
    private Messages messages;

    @Persist
    @Property
    private Date startDate;

    @Persist
    @Property
    private Date endDate;

    @Persist
    @Property
    private String origin;

    @Persist
    @Property
    private String destination;

    @Property
    @Persist
    private List<AirportStub> allAirportStubs;

    @Property
    @Persist
    private boolean roundTrip;


    //----------------- Slider ------------

    @Property
    private int slideZone;

    //---------------------------------------

    @InjectPage
    private ResultsPage resultsPage;

    @Log
    @OnEvent(value = EventConstants.SUCCESS, component = "SearchForm")
    Object startSearch() {
        String originCode = origin.substring(1,4);
        String destinationCode = destination.substring(1,4);

        AirportStub departureAirport =  airportInformationDAO.getAirportData(originCode);
        departureAirport.setCode(originCode);
        AirportStub destinationAirport = airportInformationDAO.getAirportData(destinationCode);
        destinationAirport.setCode(destinationCode);

        if(departureAirport==null || destinationAirport==null){
            return null;
        }

        if(endDate.before(startDate) || endDate.equals(startDate)){
            searchForm.recordError(messages.get("error.validateenddate"));
            return null;
        }
        Search search = new Search();


        search.setRoundTrip(roundTrip);
        search.setDepartureAirport(departureAirport);
        search.setDestinationAirport(destinationAirport);
        search.setDepartureDate(startDate);
        search.setReturnDate(endDate);
        search.setNumberAdults(1);
        search.setNumberChildren(0);
        search.setNewBorns(0);

        List<Result> results = flightSearchConnector.searchOneWayFlights(search);
        resultsPage.setup(search,results);
        return resultsPage;
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
