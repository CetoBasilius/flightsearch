package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.flightresult.Flight;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
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
public class FlightResultFilterImpl implements FlightResultFilter {

    private static final Logger logger = LoggerFactory.getLogger(FlightResultFilterImpl.class);

    private boolean wasResultFiltered = false;

    private int filterBudget;
    private int filterSegments;


    public FlightResult filterSearch(FlightResult inFlightResult, int budget, int segments) {

        FlightResult returnFlightResult = new FlightResult();

        try {
            returnFlightResult = (FlightResult) inFlightResult.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("cloning "+this.getClass()+" failed");
        }

        List<Flight> newList = new ArrayList<Flight>();

        List<Flight> flights = inFlightResult.getFlights();
        for(int index = 0; index < flights.size();index++){
            Flight inFlight = flights.get(index);
            if(inFlight.getPriceInfo().getTotal().getFare().intValue()<budget){
                newList.add(inFlight);
            }
        }
        returnFlightResult.setSearchedPrice(budget);
        returnFlightResult.setFlights(newList);

        wasResultFiltered = true;
        return returnFlightResult;
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
