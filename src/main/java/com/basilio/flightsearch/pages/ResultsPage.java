package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.CustomPagedLoop;
import com.basilio.flightsearch.components.Window;
import com.basilio.flightsearch.core.FlightResultFilter;
import com.basilio.flightsearch.core.FlightResultFilterImpl;
import com.basilio.flightsearch.core.helpers.NumberHelper;
import com.basilio.flightsearch.connectors.air.AirportInformationConnector;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.flightresult.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/26/12
 * Time: 8:09 PM
 * The user will be able to see the search results here.
 * SearchPage results include Origin, Destination, Price, Distance, Estimated Time.
 */

@SupportsInformalParameters
@GuestAccess
@Import(library = "customelements.js")
public class ResultsPage {

    @Property
    @Inject
    private Request request;
    @Inject
    private AirportInformationConnector airportInformationConnector;
    //-------------------------------------------------------
    @Property
    private int outSegmentWindowIndex;
    @Property
    private int inSegmentWindowIndex;
    //------------------------------------------------------
    @Component
    private Form filterForm;
    @Persist
    @Property
    private int budgetFilterSlider;
    @Persist
    @Property
    private String segmentFilterRadioSelectedValue;
    @Persist
    @Property
    private String segmentFilterRadioOne;
    @Persist
    @Property
    private String segmentFilterRadioTwoPlus;
    @Persist
    @Property
    private String segmentFilterRadioAny;
    @Persist
    @Property
    private String radioAllDurations;
    @Persist
    @Property
    private String durationFilterRadioSelectedValue;
    @Persist
    @Property
    private String radioAllTypes;
    @Persist
    @Property
    private String typeFilterRadioSelectedValue;
    //----------------------------------------------
    @Persist
    @Property
    private int minPriceFilter;
    @Persist
    @Property
    private int maxPriceFilter;
    @Persist
    @Property
    private int priceFilterSteps;
    //-----------------------------------------
    @Persist
    @Property
    private String customHandleCSS;
    @Persist
    @Property
    private String customTrackCSS;
    @Persist
    @Property
    private String customValueCSS;

    //---------------------------------------
    @Component
    private Form buyForm;
    @Component
    private Form errorForm;

    @Component
    private CustomPagedLoop customPagedLoop;

    @Persist(PersistenceConstants.SESSION)
    private FlightResultFilter flightResultFilter;
    @Persist(PersistenceConstants.SESSION)
    private FlightResult flightResult;
    @Persist(PersistenceConstants.SESSION)
    private FlightResult showingFlightResult;
    @Persist(PersistenceConstants.SESSION)
    private FlightResult filteredFlightResult;
    @Persist(PersistenceConstants.SESSION)
    private FlightSearch flightSearch;

    @Property
    @Persist
    private Flight flight;
    @Property
    private int flightIndex;
    @Property
    private Route outboundRoute;
    @Property
    private Route inboundRoute;
    @Property
    private Segment outSegment;
    @Property
    private Segment inSegment;
    @Property
    private boolean emptyResult;
    @Inject
    private Messages messages;
    @Property
    @Persist
    private int rowsPerPage;
    @Property
    private int outBoundIndex;
    @Property
    private int inBoundIndex;
    @Property
    @Persist
    private String outRadioSelectedValue;
    @Property
    @Persist
    private String inRadioSelectedValue;
    @Persist
    private String bought;

    @Component(parameters = {"style=greylighting",
            "show=false",
            "modal=true",
            "width=500",
            "title=literal:Outbound flight details"})
    private Window outboundWindow;

    @Component(parameters = {"style=greylighting",
            "show=false",
            "modal=true",
            "width=500",
            "title=literal:Inbound flight details"})
    private Window inboundWindow;

    @InjectPage
    private SuggestPage suggestPage;
    @InjectPage
    private ConfirmPage confirmPage;

    public void setup(FlightSearch flightSearch, FlightResult flightResult) {
        customPagedLoop.setCurrentPage(1);
        this.flightResult = flightResult;
        this.filteredFlightResult = flightResult;
        this.showingFlightResult = flightResult;

        this.flightSearch = flightSearch;

        if (flightResultFilter == null) {
            flightResultFilter = new FlightResultFilterImpl();
            segmentFilterRadioSelectedValue = Integer.toString(Flight.ANY_SEGMENTS);
        } else {
            flightResultFilter.setWasResultFiltered(false);
        }

    }

    void setupRender() {
        customHandleCSS = "slider-handle-custom";
        customTrackCSS = "slider-track-custom";
        customValueCSS = "slider-value-custom";

        if (showingFlightResult != null) {
            List<Facet> facets = showingFlightResult.getMeta().getFacets();
            if (facets != null) {
                if(facets.size() > 0){
                    Facet minmaxFacet = facets.get(2);
                    minPriceFilter = minmaxFacet.getMin().intValue() - (minmaxFacet.getMin().intValue() % 100);
                    maxPriceFilter = minmaxFacet.getMax().intValue() + (100 - (minmaxFacet.getMax().intValue() % 100));
                    priceFilterSteps = (maxPriceFilter - minPriceFilter) / 100;

                    budgetFilterSlider = flightSearch.getBudgetDollars();
                    emptyResult = false;
                }
            } else {
                emptyResult = true;
            }

            radioAllDurations = "";

            segmentFilterRadioAny = Integer.toString(Flight.ANY_SEGMENTS);
            segmentFilterRadioOne = Integer.toString(Flight.ONE_SEGMENT);
            segmentFilterRadioTwoPlus = Integer.toString(Flight.TWO_OR_MORE_SEGMENTS);

            radioAllTypes = "";

        }


        if (flightResultFilter == null) {
            flightResultFilter = new FlightResultFilterImpl();
            segmentFilterRadioSelectedValue = Integer.toString(Flight.ANY_SEGMENTS);
        }


        rowsPerPage = 6;
        windowNumber = 0;
        //TODO: rowsperpage must be retrieved from user settings.
    }

    public String getBudgetDollarsString() {
        return String.valueOf(flightSearch.getBudgetDollars());
    }

    public String getIsOnPriceRangeCSS() {
        DecimalFormat df = new DecimalFormat("#.00");

        if (flight.getPriceInfo().getTotal().getFare().floatValue() > flightSearch.getBudgetDollars()) {
            return "flightboxnotinrange";
        }
        return "roundedbox";
    }

    public boolean getIsFlightRound() {
        return flightSearch.isRoundTrip();
    }

    public boolean getIsOnPriceRangeBoolean() {
        DecimalFormat df = new DecimalFormat("#.00");

        return flight.getPriceInfo().getTotal().getFare().floatValue() <= flightSearch.getBudgetDollars();
    }


    public boolean getIsThereNextOutSegment() {
        if (outSegmentWindowIndex < outboundRoute.getSegments().size() - 1) {
            return true;
        }
        return false;
    }

    public boolean getIsThereNextInSegment() {
        if (inSegmentWindowIndex < inboundRoute.getSegments().size() - 1) {
            return true;
        }
        return false;
    }

    public String getInSegmentWaitDesc() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("There will be a wait of ");
        buffer.append(inboundRoute.getWaitDescription(inSegmentWindowIndex, inSegmentWindowIndex + 1));
        return buffer.toString();
    }

    public String getOutSegmentWaitDesc() {
        return String.format("There will be a wait of  %s", outboundRoute.getWaitDescription(outSegmentWindowIndex, outSegmentWindowIndex + 1));
    }

    public String getOutboundRouteShort() {
        //Flight,OutboundRoute
        return (this.getNumFlightsBeforeCurrentPage() + flightIndex) + "," + outBoundIndex;
    }

    public String getInboundRouteShort() {
        //,InboundRoute
        return "," + inBoundIndex;
    }


    public String getNumFlights() {
        int numFlights = 0;
        if (showingFlightResult != null) {
            numFlights = showingFlightResult.getFlights().size();
        }
        return Integer.toString(numFlights);
    }

    public String getFlightPrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(flight.getPriceInfo().getTotal().getFare().floatValue()) + " USD";
    }

    public String getBoughtItem() {
        return bought;
    }

    public String getOutSegmentArriveInfo() {
        return outSegment.getArrivalDescription();
    }

    public String getOutSegmentLeaveInfo() {
        return outSegment.getDepartureDescription();
    }

    public String getOutSegmentLeaveHourInfo() {
        return outSegment.getDepartureHourDescription();
    }

    public String getInSegmentLeaveHourInfo() {
        return inSegment.getDepartureHourDescription();
    }

    public String getOutSegmentArriveHourInfo() {
        return outSegment.getArrivalHourDescription();
    }

    public String getInSegmentArriveHourInfo() {
        return inSegment.getArrivalHourDescription();
    }

    public String getOutSegmentStopInfo() {
        return outSegment.getStopDescription();
    }

    public String getInSegmentStopInfo() {
        return inSegment.getStopDescription();
    }


    public boolean getOutSegmentHasStops() {
        return outSegment.getStopovers() != null && outSegment.getStopovers().size() > 0;
    }

    public boolean getInSegmentHasStops() {
        return inSegment.getStopovers() != null && inSegment.getStopovers().size() > 0;
    }


    public String getInSegmentArriveInfo() {
        return inSegment.getArrivalDescription();
    }

    public String getInSegmentLeaveInfo() {
        return inSegment.getDepartureDescription();
    }

    public boolean getInContinueRenderingArrow() {
        if (inRouteSegmentInfoIndex < inboundRoute.getSegments().size()) {
            return true;
        }
        return false;
    }

    public boolean getOutContinueRenderingArrow() {
        if (outRouteSegmentInfoIndex < outboundRoute.getSegments().size()) {
            return true;
        }
        return false;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "buyForm")
    public Object buyTicket() {
        int selectedOutRouteIndex = -1;
        int selectedFlightIndex = -1;
        int selectedInRouteIndex = -1;

        if (StringUtils.isNotBlank(outRadioSelectedValue)) {

            bought = this.outRadioSelectedValue;
        } else {
            errorForm.recordError(messages.get("error.mustselectoutboundroute"));
            return null;
        }
        if (flightSearch.isRoundTrip()) {

            if (StringUtils.isNotBlank(inRadioSelectedValue)) {
                bought += this.inRadioSelectedValue;

                String[] numbers = bought.split(",");
                if(numbers.length==3){
                    selectedFlightIndex = Integer.parseInt(numbers[0]);
                    selectedOutRouteIndex = Integer.parseInt(numbers[1]);
                    selectedInRouteIndex = Integer.parseInt(numbers[2]);
                } else{
                    errorForm.recordError(messages.get("error.somethingwrong"));
                }
            } else {
                errorForm.recordError(messages.get("error.mustselectinboundroute"));
                return null;
            }
        } else {
            String[] numbers = bought.split(",");
            if(numbers.length==2){
                selectedFlightIndex = Integer.parseInt(numbers[0]);
                selectedOutRouteIndex = Integer.parseInt(numbers[1]);

            } else{
                errorForm.recordError(messages.get("error.somethingwrong"));
            }
        }

        Flight chosenFlightCombo = null;
        try {
            chosenFlightCombo = (Flight) flightResult.getFlights().get(selectedFlightIndex).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(chosenFlightCombo != null){
            Route ChosenOutboundRoute = flightResult.getFlights().get(selectedFlightIndex).getOutboundRoutes().get(selectedOutRouteIndex);
            Route chosenInboundRoute = null;
            if(selectedInRouteIndex>=0){
                chosenInboundRoute = flightResult.getFlights().get(selectedFlightIndex).getInboundRoutes().get(selectedInRouteIndex);
            }

            chosenFlightCombo.setOutboundRoutes(new ArrayList<Route>());
            chosenFlightCombo.setInboundRoutes(new ArrayList<Route>());

            chosenFlightCombo.getOutboundRoutes().add(ChosenOutboundRoute);
            if(selectedInRouteIndex>=0){
                chosenFlightCombo.getInboundRoutes().add(chosenInboundRoute);
            }

            confirmPage.setup(this.flightSearch,chosenFlightCombo);
            //suggestPage.setup(this.flightResult.getFlights().get(0));
        }

        return confirmPage;
    }


    //-----------------------------------------------

    @OnEvent(value = "applyfilter")
    public Object filterResults() {
        this.flightSearch.setBudgetDollars(budgetFilterSlider);

        int segmentOption = Integer.parseInt(segmentFilterRadioSelectedValue);

        filteredFlightResult = flightResultFilter.filterSearch(flightResult, budgetFilterSlider, segmentOption);
        showingFlightResult = filteredFlightResult;

        customPagedLoop.setCurrentPage(1);
        return null;
    }

    @OnEvent(value = "disablefilter")
    public Object onDisableFilter() {
        flightResultFilter.setWasResultFiltered(false);
        showingFlightResult = flightResult;
        return null;
    }

    private int getNumFlightsBeforeCurrentPage() {
        if (customPagedLoop.getCurrentPage() < 1) {
            customPagedLoop.setCurrentPage(1);
        }
        return (customPagedLoop.getCurrentPage() - 1) * rowsPerPage;
    }

    @Property
    private final ValueEncoder<Route> inboundRoutesValueEncoder = new ValueEncoder<Route>() {

        public String toClient(Route answer) {
            int in = flight.getInboundRoutes().indexOf(answer);
            return String.valueOf(in);
        }

        public Route toValue(String str) {
            return null;
        }
    };

    @Property
    private final ValueEncoder<Segment> inSegmentsValueEncoder = new ValueEncoder<Segment>() {

        public String toClient(Segment answer) {
            int in = inboundRoute.getSegments().indexOf(answer);
            return String.valueOf(in);
        }

        public Segment toValue(String str) {
            return null;
        }
    };

    @Property
    private final ValueEncoder<Route> outboundRoutesValueEncoder = new ValueEncoder<Route>() {

        public String toClient(Route answer) {
            int in = flight.getOutboundRoutes().indexOf(answer);
            return String.valueOf(in);
        }

        public Route toValue(String str) {
            return null;
        }
    };

    @Property
    private final ValueEncoder<Segment> outSegmentsValueEncoder = new ValueEncoder<Segment>() {

        public String toClient(Segment answer) {
            int in = outboundRoute.getSegments().indexOf(answer);
            return String.valueOf(in);
        }

        public Segment toValue(String str) {
            return null;
        }
    };

    //-----------------------------------------------------
    public String getOutRouteSegmentNumber() {
        return outboundRoute.getSegmentsNumber();
    }

    public String getInRouteSegmentNumber() {
        return inboundRoute.getSegmentsNumber();
    }

    @Property
    private int outRouteSegmentInfoIndex;

    @Property
    private int inRouteSegmentInfoIndex;

    @Property
    private String outSegmentInfo;

    @Property
    private String inSegmentInfo;

    public String[] getOutRouteSegmentInfo() {
        return outboundRoute.getSegmentsDescription();
    }

    public String[] getInRouteSegmentInfo() {
        return inboundRoute.getSegmentsDescription();
    }

    public String getOutRouteSegmentInfoCommas() {
        return routeSegmentInfoCommas(outboundRoute);
    }

    public String getInRouteSegmentInfoCommas() {
        return routeSegmentInfoCommas(inboundRoute);
    }

    private String routeSegmentInfoCommas(Route route) {
        StringBuffer buffer = new StringBuffer();

        String[] segmentsDescription = route.getSegmentsDescription();
        int length = segmentsDescription.length;
        for (int index = 0; index < length; index++) {
            buffer.append(segmentsDescription[index]);
            if (index < length - 1) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

    public String getOutRouteDurationInfo() {
        return outboundRoute.getDurationDescription();
    }

    public String getInRouteDurationInfo() {
        return inboundRoute.getDurationDescription();
    }


    public String getInRouteLeaveInfo() {
        return inboundRoute.getLeaveDescription();
    }

    //-------------------------------------------

    public String getOutRouteScheduleInfo() {
        return outboundRoute.getScheduleDescription();
    }

    public String getInRouteScheduleInfo() {
        return inboundRoute.getScheduleDescription();
    }


    public String getInRouteType() {
        return WordUtils.capitalize(inboundRoute.getSegments().get(0).getMarketingCabinTypeCode().toLowerCase());

    }

    //-------------------------------------------
    public String getSearchDescription() {
        return flightSearch != null ? flightSearch.getDescription() : "No search was made.";
    }

    public String getResultDescription() {
        return showingFlightResult.getDescription();
    }

    @Persist
    private int windowNumber;

    public String getOutWindowNumber() {
        int returnNumber = windowNumber;
        windowNumber++;
        return String.valueOf(returnNumber);
    }

    public String getInWindowNumber() {
        int returnNumber = windowNumber;
        windowNumber++;
        return "" + returnNumber;
    }

    public String getStaticOutWindowNumber() {
        int returnNumber = windowNumber - 1;
        return "" + returnNumber;
    }

    public String getStaticInWindowNumber() {
        int returnNumber = windowNumber - 1;
        return "" + returnNumber;
    }

    public Segment[] getOutSegments() {
        return outboundRoute.getSegments().toArray(new Segment[0]);
    }


    public Segment[] getInSegments() {
        return inboundRoute.getSegments().toArray(new Segment[0]);
    }

    public Route[] getOutRoutes() {
        return flight.getOutboundRoutes().toArray(new Route[0]);
    }

    public Route[] getInRoutes() {
        return flight.getInboundRoutes().toArray(new Route[0]);
    }

    public Flight[] getFlights() {
        if (showingFlightResult != null) {
            if (flightSearch.isDirectFlight()) {
                return showingFlightResult.getDirectFlights().toArray(new Flight[0]);
            } else {

                return showingFlightResult.getFlights().toArray(new Flight[0]);
            }
        }

        Flight[] resultArray = new Flight[1];
        resultArray[0] = new Flight();
        emptyResult = true;
        return resultArray;
    }

    public String getOutSegmentWindowDescription() {
        return segmentWindowDescription(outSegment, outSegmentWindowIndex);
    }

    public String getInSegmentWindowDescription() {
        return segmentWindowDescription(inSegment, inSegmentWindowIndex);
    }

    public String segmentWindowDescription(Segment segment, int index) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(NumberHelper.ordinal(index + 1));
        buffer.append(" segment - Flight ");
        buffer.append(segment.getFlightNumber());
        buffer.append(" - ");
        buffer.append(WordUtils.capitalize(segment.getMarketingCabinTypeCode().toLowerCase()));
        buffer.append(" - ");
        buffer.append(segment.getOperatingCarrierDescription());
        return buffer.toString();
    }

    public String getFlightOutLabelDesc() {
        return flight.getOutDescription();
    }

    public String getFlightInLabelDesc() {
        return flight.getInDescription();
    }

    public String getOutSegmentWindowMoreInfo() {
        return outSegment.getArrivalDescription();
    }

    public String getOutSegmentDurationInfo() {
        return outSegment.getDurationDescription();
    }

    public String getInSegmentDurationInfo() {
        return inSegment.getDurationDescription();
    }

    public FlightSearch getFlightSearch() {
        return flightSearch;
    }

    public void setFlightSearch(FlightSearch flightSearch) {
        this.flightSearch = flightSearch;
    }

    public FlightResult getFlightResult() {
        return showingFlightResult;
    }

    public void setFlightResult(FlightResult flightResult) {
        this.showingFlightResult = flightResult;
        this.flightResult = flightResult;
        this.filteredFlightResult = flightResult;
    }

    @InjectPage
    private MapPage mapPage;

    public Object onActionFromOutViewMap(String airportCodesString) {
        return viewMap(airportCodesString);
    }

    public Object onActionFromInViewMap(String airportCodesString) {
        return viewMap(airportCodesString);
    }

    public String getFilterDescription() {
        return flightResultFilter.getFilterDescription();
    }

    public Object viewMap(String airportCodesString) {
        final String[] airportCodes = airportCodesString.split(",");
        final List<Double> setupList = new ArrayList<Double>();
        final List<String> setupDescList = new ArrayList<String>();
        final List<AirportStub> airportStubList = new ArrayList<AirportStub>();

        for (int index = 0; index < airportCodes.length; index++) {
            airportStubList.add(new AirportStub(airportCodes[index], ""));
        }

        List<AirportStub> finalAirportStubList = airportInformationConnector.getAirportData(airportStubList);

        for (int index = 0; index < finalAirportStubList.size(); index++) {
            AirportStub airportStub = finalAirportStubList.get(index);
            setupList.add(new Double(airportStub.getLatitude()));
            setupList.add(new Double(airportStub.getLongitude()));
            setupDescList.add(airportStub.getDescriptor());
        }

        mapPage.setupMapPage(setupList, setupDescList);
        return mapPage;
    }

    public AirportInformationConnector getAirportInformationConnector() {
        return airportInformationConnector;
    }

    public void setAirportInformationConnector(AirportInformationConnector airportInformationConnector) {
        this.airportInformationConnector = airportInformationConnector;
    }

    public MapPage getMapPage() {
        return mapPage;
    }

    public void setMapPage(MapPage mapPage) {
        this.mapPage = mapPage;
    }

    public FlightResult getFilteredFlightResult() {
        return filteredFlightResult;
    }

    public FlightResult getShowingFlightResult() {
        return showingFlightResult;
    }

    public CustomPagedLoop getCustomPagedLoop() {
        return customPagedLoop;
    }

    public void setCustomPagedLoop(CustomPagedLoop customPagedLoop) {
        this.customPagedLoop = customPagedLoop;
    }

    public FlightResultFilter getFlightResultFilter() {
        return flightResultFilter;
    }

    public void setFlightResultFilter(FlightResultFilter flightResultFilter) {
        this.flightResultFilter = flightResultFilter;
    }

    public String getSegmentFilterRadioSelectedValueString() {
        return segmentFilterRadioSelectedValue;
    }

    public void setSegmentFilterRadioSelectedValueString(String segmentFilterRadioSelectedValue) {
        this.segmentFilterRadioSelectedValue = segmentFilterRadioSelectedValue;
    }

    public void setWindowNumber(int windowNumber) {
        this.windowNumber = windowNumber;
    }

    public Route getInboundRouteValue() {
        return inboundRoute;
    }

    public void setInboundRouteValue(Route inboundRoute) {
        this.inboundRoute = inboundRoute;
    }

    public Flight getFlightValue() {
        return flight;
    }

    public void setFlightValue(Flight flight) {
        this.flight = flight;
    }

    public Route getOutboundRouteValue() {
        return outboundRoute;
    }

    public void setOutboundRouteValue(Route outboundRoute) {
        this.outboundRoute = outboundRoute;
    }
}
