package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

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
    private ResultCreator result;

    public void setup(Search search,ResultCreator result)
    {
        this.result = result;
        result.createGoodResult();
        this.origin = search.getDepartureAirport().getCode();
        this.destination = search.getDestinationAirport().getCode();
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
            resultArray[0] = "results was empty or null";
        }

        return resultArray;
    }

}
