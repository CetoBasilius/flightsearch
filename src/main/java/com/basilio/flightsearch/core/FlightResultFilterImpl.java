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

    private boolean wasResultFiltered;

    private int filterBudget;
    private int filterSegments;


    public FlightResult filterSearch(FlightResult inFlightResult, int budget, int segments) {

        FlightResult returnFlightResult = new FlightResult();

        try {
            returnFlightResult = (FlightResult) inFlightResult.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("cloning "+this.getClass()+" failed");
        }

        List<Flight> flights = inFlightResult.getFlights();

        flights = filterFLightListByBudget(flights, budget);
        flights = filterFLightListBySegments(flights, segments);

        returnFlightResult.setSearchedPrice(budget);
        returnFlightResult.setFlights(flights);

        wasResultFiltered = true;
        return returnFlightResult;
    }

    private List<Flight> filterFLightListBySegments(List<Flight> flightList, int segments) {
        List<Flight> newList = new ArrayList<Flight>();

        for(int index = 0; index < flightList.size();index++){
            Flight inFlight = flightList.get(index);
            if(inFlight.outboundHasSegments(segments) && inFlight.inboundHasSegments(segments)){
                newList.add(inFlight);
            }
        }
        return newList;

    }

    private List<Flight> filterFLightListByBudget(List<Flight> flightList, int budget) {
        List<Flight> newList = new ArrayList<Flight>();

        for(int index = 0; index < flightList.size();index++){
            Flight inFlight = flightList.get(index);
            if(inFlight.getPriceInfo().getTotal().getFare().intValue()<budget){
                newList.add(inFlight);
            }
        }
        return newList;
    }

    public String getFilterDescription() {
        StringBuffer buffer = new StringBuffer();

        if(!wasResultFiltered){
            buffer.append("Currently showing all results.");
        } else {
            buffer.append("Currently filtering results");
        }

        return buffer.toString();
    }

    public boolean getIsResultFiltered() {
        return wasResultFiltered;
    }

    public void setWasResultFiltered(boolean wasResultFiltered) {
        this.wasResultFiltered = wasResultFiltered;
    }

}
