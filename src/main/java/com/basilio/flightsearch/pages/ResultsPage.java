package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.Result;
import com.basilio.flightsearch.entities.Search;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

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

    @Persist(PersistenceConstants.FLASH)
    private Result result;

    public void setup(Search search,List<Result> results)
    {
        result = results.get(0);
        this.origin = search.getDepartureAirport().getDescriptor();
        this.destination = search.getDestinationAirport().getDescriptor();
    }

    @Property
    private String _resultString;

    public String[] getResults()
    {
        String[] resultArray = null;
        if(result!=null){
            resultArray = new String[result.getResult().size()];
            for(int index = 0;index<result.getResult().size();index++){
                resultArray[index] = (result.getResult().get(index));
            }
        }

        if(resultArray == null){
            resultArray= new String[1];
            resultArray[0] = "results was empty";
        }

        return resultArray;
    }

}