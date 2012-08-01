package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.airport.Airport;
import com.google.gson.Gson;
import org.apache.tapestry5.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/30/12
 * Time: 4:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class AirportCreator {

    private static final Logger logger = LoggerFactory.getLogger(AirportCreator.class);
    private String resultString;
    private Gson gson;

    public AirportCreator() {
        gson = new Gson();
    }

    public String getResultString() {
        return resultString;
    }

    @Log
    public Airport getGoodResult(){
        Airport goodResult = null;
        try{
            goodResult = gson.fromJson(getAllResultString(),Airport.class);
        } catch(Exception e){
            logger.error("Could not create good result object.");
        }
        return goodResult;
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
