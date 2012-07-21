package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.CustomPagedLoop;
import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.*;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.chenillekit.tapestry.core.components.PagedLoop;

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

@GuestAccess
public class ResultsPage {


    @Property
    @Persist(PersistenceConstants.SESSION)
    private String origin;

    @Property
    @Persist(PersistenceConstants.SESSION)
    private String destination;

    @Property
    private double latin = 0;

    @Property
    private double lngin = 0;

    @Persist(PersistenceConstants.SESSION)
    @Property
    private List<Double> coordinatesParameter;

    @Persist(PersistenceConstants.SESSION)
    @Property
    private String visualizingFlight;

    @Component
    private CustomPagedLoop customPagedLoop;

    @Persist(PersistenceConstants.SESSION)
    private Result result;

    @Persist(PersistenceConstants.SESSION)
    private Search search;

    @Property
    private Flights flight;

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

    public void setup(Search search,Result result)
    {
        this.result = result;
        this.search = search;

        this.origin = search.getDepartureAirport().getCode();
        this.destination = search.getDestinationAirport().getCode();
    }

    public void setupGMap(List<Double> coordinatesin){
        coordinatesParameter = coordinatesin;
    }

    public String getNumFlights(){
        int numFlights = 0;
        if(result != null){
            numFlights = result.getFlights().size();
        }
        return Integer.toString(numFlights);
    }

    public String getFlightPrice(){

        return String.valueOf(flight.getPriceInfo().getTotal().getFare().intValue())+" dollars";
    }

    public void onActionFromViewDepart(String id)
    {
        visualizingFlight = "Visualizing flight "+id;
        List<Double> setupList = new ArrayList<Double>();
        setupList.add(-90.0+(Math.random()*180));setupList.add(20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-50.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-179.0+(Math.random()*20));

        this.setupGMap(setupList);
    }

    public void onActionFromViewReturn(String id)
    {
        visualizingFlight = "Visualizing flight "+id;
        List<Double> setupList = new ArrayList<Double>();
        setupList.add(-90.0+(Math.random()*180));setupList.add(20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-50.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-179.0+(Math.random()*20));

        this.setupGMap(setupList);
    }

    public String getFlightDescription(){
        return flight.getDescription();
    }

    public String getOutSegmentInfo(){
        return outSegment.getDescription();
    }

    public String getInSegmentInfo(){
        return inSegment.getDescription();
    }

    public String getOutRouteInfo(){
        return outboundRoute.getDescription();
    }

    public String getInRouteInfo(){
        return inboundRoute.getDescription();
    }

    public String getSearchDescription(){
        if(search!=null){
            return search.getDescription();
        }
        return "No search was made.";
    }

    public String getResultDescription(){
        return result.getDescription();
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
        List<Segments> inSegmentsList = outboundRoute.getSegments();
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


}
