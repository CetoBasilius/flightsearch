package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.result.Flight;
import com.basilio.flightsearch.entities.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/31/12
 * Time: 8:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultFilterImpl implements ResultFilter{

    private static final Logger logger = LoggerFactory.getLogger(ResultFilterImpl.class);

    private boolean wasResultFiltered = false;

    private int filterBudget;
    private int filterSegments;


    public Result filterSearch(Result inResult, int budget, int segments) {

        Result returnResult = new Result();

        try {
            returnResult = (Result)inResult.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("cloning "+this.getClass()+" failed");
        }

        List<Flight> newList = new ArrayList<Flight>();

        List<Flight> flights = inResult.getFlights();
        for(int index = 0; index < flights.size();index++){
            Flight inFlight = flights.get(index);
            if(inFlight.getPriceInfo().getTotal().getFare().intValue()<budget){
                newList.add(inFlight);
            }
        }
        returnResult.setFlights(newList);

        wasResultFiltered = true;
        return returnResult;
    }

    public String getDescription() {
        StringBuffer buffer = new StringBuffer();

        if(!wasResultFiltered){
            buffer.append("Currently showing all results.");
        } else {
            buffer.append("Currently filtering results");
        }

        return buffer.toString();
    }

    public boolean wasResultFiltered() {
        return wasResultFiltered;
    }

    public void setWasResultFiltered(boolean wasResultFiltered) {
        this.wasResultFiltered = wasResultFiltered;
    }

}
