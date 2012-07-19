package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.CustomPagedLoop;
import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.Flights;
import com.basilio.flightsearch.entities.result.Result;
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
    private String flightString;

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

    @Log
    public String[] getFlightStrings(){
        String returnString[];
        if(flight==null){
            returnString = new String[1];
            returnString[0] = "There were no results";
        }else{
            returnString = flight.toStringArray();
        }
        return returnString;
    }

    public void onActionFromView(String id)
    {
        visualizingFlight = "Visualizing flight "+id;
        List<Double> setupList = new ArrayList<Double>();
        setupList.add(-90.0+(Math.random()*180));setupList.add(20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-50.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-179.0+(Math.random()*20));

        this.setupGMap(setupList);
    }

    public Flights[] getFlights()
    {
        int numFlights = 0;
        Flights[] resultArray = null;
        if(result != null){
            if(search.isDirectFlight()){
                numFlights = result.getDirectFlights().size();
                resultArray = new Flights[numFlights];
                for(int a = 0;a<numFlights;a++){
                    resultArray[a] = result.getDirectFlights().get(a);
                }
            }else{
                numFlights = result.getFlights().size();
                resultArray = new Flights[numFlights];
                for(int a = 0;a<numFlights;a++){
                    resultArray[a] = result.getFlights().get(a);
                }
            }
        } else {
            resultArray = new Flights[1];
            resultArray[0] = new Flights();
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
