package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.CustomPagedLoop;
import com.basilio.flightsearch.components.Window;
import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.*;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.commons.lang.WordUtils;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/26/12
 * Time: 8:09 PM
 * The user will be able to see the search results here.
 * SearchPage results include Origin, Destination, Price, Distance, Estimated Time.
 */

@GuestAccess
public class ResultsPage {

    @Property
    private int outSegmentWindowIndex;

    //------------------------------------------------------
    @Component
    private Form typeFilterForm;

    @Property
    private String radioDirect;

    @Property
    private String radio1Segment;

    @Property
    private String radio2SegmentMore;

    @Property
    private String radioAllSegments;

    @Property
    @Persist
    private String segmentFilterRadioSelectedValue;

    @Property
    private String radioAllDurations;

    @Property
    @Persist
    private String durationFilterRadioSelectedValue;

    @Property
    private String radioAllTypes;

    @Property
    @Persist
    private String typeFilterRadioSelectedValue;

    //-----------------------------------------

    @Persist
    @Property
    private int minPriceFilter;

    @Persist
    @Property
    private int maxPriceFilter;

    @Persist
    @Property
    private int priceFilterSteps;

    @Property
    private int slider;

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

    @Property
    @Inject
    private Request request;

    @Component
    private CustomPagedLoop customPagedLoop;

    @Persist(PersistenceConstants.SESSION)
    private Result result;

    @Persist(PersistenceConstants.SESSION)
    private Search search;

    @Property
    @Persist
    private Flights flight;

    @Property
    private int flightIndex;

    @Property
    private OutboundRoutes outboundRoute;

    @Property
    private InboundRoutes inboundRoute;

    @Property
    private Segments outSegment;

    @Property
    private Segments inSegment;

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
    @SuppressWarnings("unused")
    private String outRadioSelectedValue;

    @Property
    @Persist
    @SuppressWarnings("unused")
    private String inRadioSelectedValue;

    @Component(parameters = {"style=bluelighting",
            "show=false",
            "modal=true",
            "width=500",
            "title=literal:Outbound flight details"})
    private Window outboundWindow;

    @Component(parameters = {"style=bluelighting",
            "show=false",
            "modal=true",
            "width=500",
            "title=literal:Inbound flight details"})
    private Window inboundWindow;



    public void setup(Search search,Result result)
    {
        customPagedLoop.setCurrentPage(1);
        this.result = result;
        this.search = search;

    }

    void setupRender()
    {
        customHandleCSS = "slider-handle-custom";
        customTrackCSS = "slider-track-custom";
        customValueCSS = "slider-value-custom";

        if(result!=null){
            Facets minmaxFacet = result.getMeta().getFacets().get(2);
            minPriceFilter = minmaxFacet.getMin().intValue()-(minmaxFacet.getMin().intValue()%100);
            maxPriceFilter = minmaxFacet.getMax().intValue()+(100-(minmaxFacet.getMax().intValue()%100));
            priceFilterSteps = (maxPriceFilter-minPriceFilter)/100;

            slider = search.getBudgetDollars();
        }


        rowsPerPage = 2;
        windowNumber = 0;
        //TODO: rowsperpage must be retrieved from user settings.
    }

    public String getBudgetDollarsString(){
        return String.valueOf(search.getBudgetDollars());
    }

    public String getIsOnPriceRangeCSS(){
        DecimalFormat df = new DecimalFormat("#.00");

        if(flight.getPriceInfo().getTotal().getFare().floatValue()>search.getBudgetDollars()){
            return "flightboxnotinrange";
        }
        return "roundedbox";
    }

    public boolean getIsOnPriceRangeBoolean(){
        DecimalFormat df = new DecimalFormat("#.00");

        if(flight.getPriceInfo().getTotal().getFare().floatValue()>search.getBudgetDollars()){
            return false;
        }
        return true;
    }




    public String getOutboundRouteShort(){
        //TODO: this method gives me the correct value
        return flightIndex+","+outBoundIndex+","+(this.getNumFlightsBeforeCurrentPage()+flightIndex);
    }

    public String getInboundRouteShort(){
        return flightIndex+","+inBoundIndex+","+(this.getNumFlightsBeforeCurrentPage()+flightIndex);
    }


    public String getNumFlights(){
        int numFlights = 0;
        if(result != null){
            numFlights = result.getFlights().size();
        }
        return Integer.toString(numFlights);
    }

    public String getFlightPrice(){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(flight.getPriceInfo().getTotal().getFare().floatValue())+" USD";
    }

    @Persist
    private String bought;

    public String getBoughtItem(){
        return bought;
    }

    public String getFlightDescription(){
        return flight.getDescription();
    }

    public String getOutSegmentArriveInfo(){
        return outSegment.getArrivalDescription();
    }

    public String getOutSegmentLeaveInfo(){
        return outSegment.getDepartureDescription();
    }



    public String getInSegmentArriveInfo(){
        return inSegment.getArrivalDescription();
    }

    public String getInSegmentLeaveInfo(){
        return inSegment.getDepartureDescription();
    }

    public boolean getInContinueRenderingArrow(){
        if(inRouteSegmentInfoIndex<inboundRoute.getSegments().size()){
            return true;
        }
        return false;
    }

    public boolean getOutContinueRenderingArrow(){
        if(outRouteSegmentInfoIndex<outboundRoute.getSegments().size()){
            return true;
        }
        return false;
    }

    @Log
    @OnEvent(value = EventConstants.SUCCESS, component = "buyForm")
    public Object buyTicket(){
        if(StringUtils.isNotBlank(outRadioSelectedValue)){
            bought = "SVO:"+this.outRadioSelectedValue;
        } else {
            errorForm.recordError(messages.get("error.mustselectoutboundroute"));
            return null;
        }
        if(search.isRoundTrip()){
            if(StringUtils.isNotBlank(inRadioSelectedValue)){
                bought += " SVI:"+inRadioSelectedValue;
            } else {
                errorForm.recordError(messages.get("error.mustselectinboundroute"));
                return null;
            }
        }

        return null;
    }
    //-----------------------------------------------

    @Log
    @OnEvent(value = EventConstants.SUCCESS, component = "typeFilterForm")
    public Object filterResults(){


        return null;
    }


    private int getNumFlightsBeforeCurrentPage() {
        if(customPagedLoop.getCurrentPage()<1){
            customPagedLoop.setCurrentPage(1);
        }
        return (customPagedLoop.getCurrentPage()-1)*rowsPerPage;
    }
//-----------------------------------------------------
    @Property
    private final ValueEncoder<InboundRoutes> inboundRoutesValueEncoder = new ValueEncoder<InboundRoutes>() {

        public String toClient(InboundRoutes answer) {
            int in = flight.getInboundRoutes().indexOf(answer);
            return String.valueOf(in);
        }

        public InboundRoutes toValue(String str) {
            if(StringUtils.isNotBlank(str)){
                int numberOfFlightsBeforeThisOne = getNumFlightsBeforeCurrentPage();
                return result.getFlights().get(numberOfFlightsBeforeThisOne+1+flightIndex).getInboundRoutes().get(Integer.parseInt(str));
            }else{
                return null;
            }
        }
    };

    @Property
    private final ValueEncoder<Segments> inSegmentsValueEncoder = new ValueEncoder<Segments>() {

        public String toClient(Segments answer) {
            int in = inboundRoute.getSegments().indexOf(answer);
            return String.valueOf(in);
        }

        public Segments toValue(String str) {
            if(StringUtils.isNotBlank(str)){
                int numberOfFlightsBeforeThisOne = getNumFlightsBeforeCurrentPage();
                return result.getFlights().get(numberOfFlightsBeforeThisOne + 1 + flightIndex).getInboundRoutes().get(inBoundIndex).getSegments().get(Integer.parseInt(str));
            }else{
                return null;
            }
        }
    };
//-----------------------------------------------------
    @Property
    private final ValueEncoder<OutboundRoutes> outboundRoutesValueEncoder = new ValueEncoder<OutboundRoutes>() {

        public String toClient(OutboundRoutes answer) {
            int in = flight.getOutboundRoutes().indexOf(answer);
            return String.valueOf(in);
        }

        public OutboundRoutes toValue(String str) {
            if(StringUtils.isNotBlank(str)){
                int numberOfFlightsBeforeThisOne = getNumFlightsBeforeCurrentPage();
                return result.getFlights().get(numberOfFlightsBeforeThisOne+flightIndex).getOutboundRoutes().get(Integer.parseInt(str));
            }else{
                return null;
            }
        }
    };

    @Property
    private final ValueEncoder<Segments> outSegmentsValueEncoder = new ValueEncoder<Segments>() {

        public String toClient(Segments answer) {
            int in = outboundRoute.getSegments().indexOf(answer);
            return String.valueOf(in);
        }

        public Segments toValue(String str) {
            if(StringUtils.isNotBlank(str)){
                int numberOfFlightsBeforeThisOne = getNumFlightsBeforeCurrentPage();
                return result.getFlights().get(numberOfFlightsBeforeThisOne + flightIndex).getOutboundRoutes().get(outBoundIndex).getSegments().get(Integer.parseInt(str));
            }else{
                return null;
            }
        }
    };
//-----------------------------------------------------
    public String getOutRouteSegmentNumber(){
        return outboundRoute.getSegmentsNumber();
    }

    public String getInRouteSegmentNumber(){
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

    public String[] getOutRouteSegmentInfo(){
        return outboundRoute.getSegmentsDescription();
    }

    public String[] getInRouteSegmentInfo(){
        return inboundRoute.getSegmentsDescription();
    }

    public String getOutRouteDurationInfo(){
        return outboundRoute.getDurationDescription();
    }

    public String getInRouteDurationInfo(){
        return inboundRoute.getDurationDescription();
    }

    public String getOutRouteArriveInfo(){
        return outboundRoute.getArriveDescription();
    }

    public String getInRouteArriveInfo(){
        return inboundRoute.getArriveDescription();
    }

    public String getOutRouteLeaveInfo(){
        return outboundRoute.getLeaveDescription();
    }

    public String getInRouteLeaveInfo(){
        return inboundRoute.getLeaveDescription();
    }



    //-------------------------------------------

    public String getOutRouteScheduleInfo(){
        return outboundRoute.getScheduleDescription();
    }

    public String getInRouteScheduleInfo(){
        return inboundRoute.getScheduleDescription();
    }

    public String getOutRouteType(){
        return WordUtils.capitalize(outboundRoute.getSegments().get(0).getMarketingCabinTypeCode().toLowerCase());
    }

    public String getInRouteType(){
        return WordUtils.capitalize(inboundRoute.getSegments().get(0).getMarketingCabinTypeCode().toLowerCase());
    }

    //-------------------------------------------
    public String getSearchDescription(){
        if(search!=null){
            return search.getDescription();
        }
        return "No search was made.";
    }

    public String getResultDescription(){
        return result.getDescription();
    }

    @Persist
    private int windowNumber;

    public String getOutWindowNumber(){
        int returnNumber = windowNumber;
        windowNumber++;
        return ""+returnNumber;
    }

    public String getInWindowNumber(){
        int returnNumber = windowNumber;
        windowNumber++;
        return ""+returnNumber;
    }

    public String getStaticOutWindowNumber(){
        int returnNumber = windowNumber-1;
        return ""+returnNumber;
    }

    public String getStaticInWindowNumber(){
        int returnNumber = windowNumber-1;
        return ""+returnNumber;
    }

    @Log
    public Segments[] getOutSegments(){
        List<Segments> outSegmentsList = outboundRoute.getSegments();
        Segments outSegments[] = new Segments[outSegmentsList.size()];
        for(int index = 0; index<outSegmentsList.size();index++){
            outSegments[index] = outSegmentsList.get(index);
        }
        return outSegments;
    }

    @Log
    public Segments[] getInSegments(){
        List<Segments> inSegmentsList = inboundRoute.getSegments();
        Segments inSegments[] = new Segments[inSegmentsList.size()];
        for(int index = 0; index<inSegmentsList.size();index++){
            inSegments[index] = inSegmentsList.get(index);
        }
        return inSegments;
    }

    @Log
    public OutboundRoutes[] getOutRoutes(){
        List<OutboundRoutes> outRoutesList = flight.getOutboundRoutes();
        OutboundRoutes outRoutes[] = new OutboundRoutes[outRoutesList.size()];
        for(int index = 0; index<outRoutesList.size();index++){
            outRoutes[index] = outRoutesList.get(index);
        }
        return outRoutes;
    }

    @Log
    public InboundRoutes[] getInRoutes(){
        List<InboundRoutes> inRoutesList = flight.getInboundRoutes();
        InboundRoutes inRoutes[] = new InboundRoutes[inRoutesList.size()];
        for(int index = 0; index<inRoutesList.size();index++){
            inRoutes[index] = inRoutesList.get(index);
        }
        return inRoutes;
    }


    @Log
    public Flights[] getFlights()
    {
        int numFlights = 0;
        Flights[] resultArray = null;
        if(result != null){
            if(search.isDirectFlight()){
                if(result.getFlights()!=null){
                    numFlights = result.getDirectFlights().size();
                    resultArray = new Flights[numFlights];
                    for(int a = 0;a<numFlights;a++){
                        resultArray[a] = result.getDirectFlights().get(a);
                    }
                }else{
                    resultArray = new Flights[1];
                    resultArray[0] = new Flights();
                    emptyResult=true;
                }
            }else{
                if(result.getFlights()!=null){
                    numFlights = result.getFlights().size();
                    resultArray = new Flights[numFlights];
                    for(int a = 0;a<numFlights;a++){
                        resultArray[a] = result.getFlights().get(a);
                    }
                }else{
                    resultArray = new Flights[1];
                    resultArray[0] = new Flights();
                    emptyResult=true;
                }
            }
        } else {
            resultArray = new Flights[1];
            resultArray[0] = new Flights();
            emptyResult=true;
        }
        return resultArray;
    }

    public String getOutSegmentWindowDescription(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(outSegmentWindowIndex);
        buffer.append(" - Flight ");
        buffer.append(outSegment.getFlightNumber());
        buffer.append(" - ");
        buffer.append(outSegment.getMarketingCabinTypeCode());
        return buffer.toString();
    }

    public String getOutSegmentWindowMoreInfo(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("MCTC:"+outSegment.getMarketingCabinTypeCode());

        buffer.append(" D:"+outSegment.getDuration());
        buffer.append(" MCC:"+outSegment.getMarketingCarrierCode());
        buffer.append(" OCC:"+outSegment.getOperatingCarrierCode());
        buffer.append(" AD:"+outSegment.getArrival().getDate());

        return buffer.toString();
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @InjectPage
    private MapPage mapPage;


    public Object onActionFromViewMap(){
        List<Double> setupList = new ArrayList<Double>();
        setupList.add(-90.0+(Math.random()*180));setupList.add(20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-50.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-179.0+(Math.random()*20));
        mapPage.setupGMap(setupList);
        return mapPage;
    }

}
