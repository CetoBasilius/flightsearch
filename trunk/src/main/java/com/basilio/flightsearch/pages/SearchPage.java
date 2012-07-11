package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.FlightSearchConnector;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.ResultOld;
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

    @Property
    private String adults = "1";

    @Property
    private String children = "0";


    //----------------- Slider ------------

    //@Component(parameters = {"value=slider1Value"})
    //private Slider slider;

    @Property
    private int slider = 500;

    //---------------------------------------

    @InjectPage
    private Tester tester;

    public Object onActionFromTester() {
        List<Double> setupList = new ArrayList<Double>();
        setupList.add(20.0);setupList.add(-20.0);
        setupList.add(50.0);setupList.add(50.0);
        setupList.add(-20.0);setupList.add(20.0);
        setupList.add(120.0);setupList.add(-120.0);

        tester.setup(setupList);
        return tester;
    }

    //---------------------------------------
    @InjectPage
    private ResultsPage resultsPage;

    @Log
    @OnEvent(value = EventConstants.SUCCESS, component = "SearchForm")
    Object startSearch() {
        String originCode = origin.substring(1,4);
        String destinationCode = destination.substring(1,4);

        AirportStub departureAirport = new AirportStub();
        departureAirport.setCode(originCode);
        AirportStub destinationAirport = new AirportStub();
        destinationAirport.setCode(destinationCode);

        if(endDate!=null){
            if(endDate.before(startDate) || endDate.equals(startDate)){
                searchForm.recordError(messages.get("error.validateenddate"));
                return null;
            }
        }
        Search search = new Search();

        search.setRoundTrip(roundTrip);
        search.setDepartureAirport(departureAirport);
        search.setDestinationAirport(destinationAirport);
        search.setDepartureDate(startDate);
        search.setReturnDate(endDate);
        search.setNumberAdults(Integer.parseInt(adults));
        search.setNumberChildren(Integer.parseInt(children));
        search.setNewBorns(0);

        List<ResultOld> results = flightSearchConnector.searchOneWayFlights(search);
        resultsPage.setup(search,results.get(0));
        return resultsPage;
    }

    @Log
    @OnEvent(value = EventConstants.ACTIVATE)
    public void getAirports() {
        if (allAirportStubs == null) {
            allAirportStubs = serviceDAO.findWithNamedQuery(AirportStub.ALL);
        }
    }

    public boolean getRoundFlightValue(){
        return roundTrip;
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
