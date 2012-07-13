package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.Result;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

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
    @Persist(PersistenceConstants.FLASH)
    private String origin;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String destination;

    @Property
    private double latin = 0;

    @Property
    private double lngin = 0;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<Double> coordinatesParameter;

    @InjectComponent
    private Zone zone;


    @Persist(PersistenceConstants.FLASH)
    private Result result;

    public void setup(Search search,Result result)
    {
        this.result = result;
        this.origin = search.getDepartureAirport().getCode();
        this.destination = search.getDestinationAirport().getCode();
    }

    public void setupGMap(List<Double> coordinatesin){
        coordinatesParameter = coordinatesin;
        System.out.println(coordinatesParameter);
    }

    Object onActionFromChangeMapTest()
    {
        List<Double> setupList = new ArrayList<Double>();
        setupList.add(-90.0+(Math.random()*180));setupList.add(20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-50.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-20.0+(Math.random()*20));
        setupList.add(-90.0+(Math.random()*180));setupList.add(-179.0+(Math.random()*20));

        this.setupGMap(setupList);
        return zone;
    }

    public String getNumFlights(){
        int numFlights = 0;
        if(result != null){
            numFlights = result.getFlights().size();
        }
        return Integer.toString(numFlights);
    }

    @Property
    private String _resultString;

    public String[] getResults()
    {
        int numFlights = 0;
        String[] resultArray = null;
        if(result != null){

            numFlights = result.getFlights().size();
            resultArray = new String[numFlights];

            for(int a = 0;a<numFlights;a++){
                resultArray[a] = "This flight has "+result.getFlights().get(a).getOutboundRoutes().size()+" outbound routes.";
            }
        } else {
            resultArray = new String[1];
            resultArray[0] = "There are no results to display.";
        }
        return resultArray;
    }

}
