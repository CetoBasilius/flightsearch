package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.google.gson.Gson;
import org.apache.tapestry5.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/5/12
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class FlightResultCreator {

    private static final Logger logger = LoggerFactory.getLogger(FlightResultCreator.class);
    private String resultString;
    private Gson gson;

    public FlightResultCreator() {
        gson = new Gson();
    }

    public String getResultString() {
        return resultString;
    }

    @Log
    public FlightResult getGoodResult(){
        FlightResult goodFlightResult = null;
        try{
            goodFlightResult = gson.fromJson(getAllResultString(),FlightResult.class);
        } catch(Exception e){
            logger.error("Could not create good result object.");
        }
        return goodFlightResult;
    }

    public String getAllResultString(){
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
