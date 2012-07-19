package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.FlightSearchConnector;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.AirportString;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.Result;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.commons.lang.StringUtils;

import java.util.*;

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
    private String Currency = "Dollars";

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
    @OnEvent(value = EventConstants.SUCCESS, component = "SearchForm")
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

    public List<AirportStub> getAirportStublist() {
        List<AirportStub> airportList = serviceDAO.findWithNamedQuery(AirportStub.ALL);
        return airportList;
    }

    @Log
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
        toggleRoundTrip();
        searchPage.setTheOrigin(origin);
        return searchPage;
    }

    @Log
    public Object onActionFromClicker2(){
        toggleRoundTrip();
        searchPage.setTheOrigin(origin);
        return searchPage;
    }

    void toggleRoundTrip() {
        if(showRoundTrip){showRoundTrip=false;}
        else{showRoundTrip=true;}
    }



    List<String> onProvideCompletionsFromOrigin(String partial) {
        getAirports();
        List<String> result = getAutoCompleteList(partial);
        if(result!=null && result.size()>0){
            origin = result.get(0);
        }
        return result;
    }

    List<String> onProvideCompletionsFromDestination(String partial) {
        getAirports();
        List<String> result = getAutoCompleteList(partial);
        if(result!=null && result.size()>0){
            destination = result.get(0);
        }
        return result;
    }

    /**
     * Autocomplete algorithm will return the airport with most occurences
     * @param partial the string provided by the user
     * @return  a list that matches the partial string
     */
    List<String> getAutoCompleteList(String partial) {
        List<AirportString> orderedResult = new ArrayList<AirportString>();
        List<String> finalResult = new ArrayList<String>();

        if(allAirportStubs!=null){
            if(partial.length()==3){
                for (AirportStub airport : allAirportStubs) {
                    if(partial.toLowerCase().equals(airport.getCode().toLowerCase())){
                        orderedResult.add(new AirportString(airport.toString(),1));
                    }
                }
            }else{

                for (AirportStub airport : allAirportStubs) {
                    int occurrences = StringUtils.countMatches(airport.toString().toLowerCase(), partial.toLowerCase());
                    if(occurrences>0){
                        orderedResult.add(new AirportString(airport.toString(),occurrences));
                    }
                }

                Collections.sort(orderedResult,Collections.reverseOrder());
            }

            for (AirportString airport : orderedResult) {
                finalResult.add(airport.getString());
            }
        }
        return finalResult;
    }

    public Date getTheStartDate() {
        return startDate;
    }

    public void setTheStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getTheEndDate() {
        return endDate;
    }

    public void setTheEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTheOrigin() {
        return origin;
    }

    public void setTheOrigin(String origin) {
        this.origin = origin;
    }

    public String getTheDestination() {
        return destination;
    }

    public void setTheDestination(String destination) {
        this.destination = destination;
    }

    public String getNumberOfAdults() {
        return adults;
    }

    public void setNumberOfAdults(String adults) {
        this.adults = adults;
    }

    public String getNumberOfChildren() {
        return children;
    }

    public void setNumberOfChildren(String children) {
        this.children = children;
    }

    public String getNumberOfInfants() {
        return infants;
    }

    public void setNumberOfInfants(String infants) {
        this.infants = infants;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setIsDirect(boolean direct) {
        this.direct = direct;
    }

    public int getSliderValue() {
        return slider;
    }

    public void setSliderValue(int slider) {
        this.slider = slider;
    }

    public List<AirportStub> getAllTheAirportStubs() {
        return allAirportStubs;
    }

    public void setAllTheAirportStubs(List<AirportStub> allAirportStubs) {
        this.allAirportStubs = allAirportStubs;
    }

    public boolean showRoundTripValue() {
        return showRoundTrip;
    }

    public void setShowRoundTripValue(boolean showRoundTrip) {
        this.showRoundTrip = showRoundTrip;
    }

}
