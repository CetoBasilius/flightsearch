package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.FlightSearchConnector;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.Result;
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
    private String adults = "1";

    @Property
    private String children = "0";

    @Property
    private String infants = "0";

    @Property
    private boolean direct;

    //----------------- Slider ------------

    @Property
    private int slider = 500;

    //---------------------------------------

    //---------------------------------------
    @InjectPage
    private ResultsPage resultsPage;

    @InjectPage
    private SearchPage searchPage;

    @Log
    @OnEvent(value = "search")
    private Object startSearch() {
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

        search.setDirectFlight(direct);
        search.setRoundTrip(showRoundTrip);
        search.setDepartureAirport(departureAirport);
        search.setDestinationAirport(destinationAirport);
        search.setDepartureDate(startDate);
        search.setReturnDate(endDate);
        search.setNumberAdults(Integer.parseInt(adults));
        search.setNumberChildren(Integer.parseInt(children));
        search.setNewBorns(Integer.parseInt(infants));

        Result result = flightSearchConnector.searchFlights(search);
        resultsPage.setup(search,result);
        return resultsPage;
    }

    @Log
    @OnEvent(value = EventConstants.ACTIVATE)
    public void getAirports() {
        if (allAirportStubs == null) {
            allAirportStubs = serviceDAO.findWithNamedQuery(AirportStub.ALL);
        }
    }

    @Property
    @Persist
    private boolean showRoundTrip;

    @Log
    public Object onActionFromClicker1(){
        if(showRoundTrip){showRoundTrip=false;}
        else{showRoundTrip=true;}
        return searchPage;
    }

    @Log
    public Object onActionFromClicker2(){
        return onActionFromClicker1();
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
