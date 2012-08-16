package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.connectors.air.FlightSearchConnector;
import com.basilio.flightsearch.persistence.ServiceDAO;
import com.basilio.flightsearch.entities.AirportString;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.flightresult.FlightSearch;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.chenillekit.tapestry.core.components.DateTimeField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Basilio
 */

@GuestAccess
public class SearchPage {

    private static final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    public static final int MILLISECONDS_IN_ONE_DAY = 86400000;
    public static final int MIN_SLIDER_VALUE = 0;
    public static final int DEFAULT_SLIDER_VALUE = 500;

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

    @Component
    private DateTimeField endDateTimeField;

    @Component
    private DateTimeField startDateTimeField;

    @Persist
    @Property
    @ActivationRequestParameter("origin")
    private String origin;

    @Persist
    @Property
    @ActivationRequestParameter("destination")
    private String destination;

    @Property
    private String Currency = "Dollars";

    @Property
    @Persist
    private List<AirportStub> allAirportStubs;

    @Property
    @Persist
    private String adults;

    @Property
    @Persist
    private String children;

    @Property
    @Persist
    private String infants;

    @Property
    @Persist
    private boolean direct;

    //----------------- Slider ------------

    @Persist
    @Property
    @ActivationRequestParameter("budget")
    private int slider;

    //---------------------------------------

    @Property
    @ActivationRequestParameter("round")
    private boolean showRoundTrip;

    void setupRender() {
        showRoundTrip = true;
        if (startDate == null) {
            Date tomorrow = new Date();
            tomorrow.setTime(tomorrow.getTime() + 2 * MILLISECONDS_IN_ONE_DAY);
            startDate = tomorrow;
        }
        if (endDate == null) {
            endDate = startDate;
        }

        if (slider <= MIN_SLIDER_VALUE) {
            slider = DEFAULT_SLIDER_VALUE;
        }

        if (adults == null) {
            adults = "1";
        }
        if (children == null) {
            children = "0";
        }
        if (infants == null) {
            infants = "0";
        }
    }


    //---------------------------------------
    @InjectPage
    private ResultsPage resultsPage;

    @OnEvent(value = EventConstants.SUCCESS, component = "SearchForm")
    private Object onSearch() {

        String originCode = origin.substring(1, 4);
        String destinationCode = destination.substring(1, 4);

        AirportStub departureAirport = new AirportStub();
        departureAirport.setCode(originCode);
        AirportStub destinationAirport = new AirportStub();
        destinationAirport.setCode(destinationCode);

        if (endDate != null) {
            if (this.showRoundTrip) {
                if (endDate.before(startDate) || endDate.equals(startDate)) {
                    searchForm.recordError(messages.get("error.validateenddate"));
                    return null;
                }
            }
        }

        if (slider < this.getMinSliderValue()) {
            searchForm.recordError(messages.get("error.notvalid"));
            logger.error("slider value was " + slider);
            return null;
        }

        int numberPersons = Integer.parseInt(adults) + Integer.parseInt(children) + Integer.parseInt(infants);

        if (Integer.parseInt(adults) <= 0) {
            searchForm.recordError(messages.get("error.adultmustgo"));
            logger.error("user tried to search for no adults");
            return null;
        }

        if (numberPersons > 8) {
            searchForm.recordError(messages.get("error.exceedpersons"));
            logger.error("user tried to search for more than 8 persons");
            return null;
        }

        FlightSearch flightSearch = createFlighSearch(departureAirport, destinationAirport);

        FlightResult flightResult = flightSearchConnector.searchFlights(flightSearch);

        resultsPage.setup(flightSearch, flightResult);
        return resultsPage;
    }

    private FlightSearch createFlighSearch(AirportStub departureAirport, AirportStub destinationAirport) {
        FlightSearch flightSearch = new FlightSearch();

        flightSearch.setBudgetDollars(slider);
        flightSearch.setDirectFlight(direct);
        flightSearch.setRoundTrip(showRoundTrip);
        flightSearch.setDepartureAirport(departureAirport);
        flightSearch.setDestinationAirport(destinationAirport);
        flightSearch.setDepartureDate(startDate);
        flightSearch.setReturnDate(endDate);
        flightSearch.setNumberAdults(Integer.parseInt(adults));
        flightSearch.setNumberChildren(Integer.parseInt(children));
        flightSearch.setNewBorns(Integer.parseInt(infants));
        return flightSearch;
    }

    private int getMinSliderValue() {
        return 200;
    }

    public List<AirportStub> getAirportStublist() {
        return serviceDAO.findWithNamedQuery(AirportStub.ALL);
    }

    public void getAirports() {
        if (allAirportStubs == null) {
            allAirportStubs = serviceDAO.findWithNamedQuery(AirportStub.ALL);
        }
    }

    void toggleRoundTrip() {
        showRoundTrip = !showRoundTrip;

    }

    List<String> onProvideCompletionsFromOrigin(String partial) {
        getAirports();
        List<String> result = getAutoCompleteList(partial);
        //CollectionUtils.isEmpty(result)   //TODO
        if (result != null && result.size() > 0) {
            origin = result.get(0);
        }
        return result;
    }

    List<String> onProvideCompletionsFromDestination(String partial) {
        getAirports();
        List<String> result = getAutoCompleteList(partial);
        if (result != null && result.size() > 0) {
            destination = result.get(0);
        }
        return result;
    }

    /**
     * Autocomplete algorithm will return the airport with most occurences
     *
     * @param partial the string provided by the user
     * @return a list that matches the partial string
     */
    List<String> getAutoCompleteList(String partial) {
        List<AirportString> orderedResult = new ArrayList<AirportString>();
        List<String> finalResult = new ArrayList<String>();

        if (allAirportStubs != null) {
            if (partial.length() == 3) {
                for (AirportStub airport : allAirportStubs) {
                    if (partial.toLowerCase().equals(airport.getCode().toLowerCase())) {
                        orderedResult.add(new AirportString(airport.toString(), 1));
                    }
                }
            } else {

                for (AirportStub airport : allAirportStubs) {
                    int occurrences = StringUtils.countMatches(airport.toString().toLowerCase(), partial.toLowerCase());
                    if (occurrences > 0) {
                        orderedResult.add(new AirportString(airport.toString(), occurrences));
                    }
                }

                Collections.sort(orderedResult, Collections.reverseOrder());
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
