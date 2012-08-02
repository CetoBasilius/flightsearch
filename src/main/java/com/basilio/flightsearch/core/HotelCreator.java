package com.basilio.flightsearch.core;

import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.hotelresult.HotelResult;
import com.google.gson.Gson;
import org.apache.tapestry5.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/2/12
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelCreator {
    private static final Logger logger = LoggerFactory.getLogger(HotelCreator.class);
    private String resultString;
    private Gson gson;

    public HotelCreator() {
        gson = new Gson();
    }

    public String getResultString() {
        return resultString;
    }

    @Log
    public HotelResult getGoodHotelResult(){
        HotelResult goodHotelResult = null;
        try{
            goodHotelResult = gson.fromJson(getAllResultString(),HotelResult.class);
        } catch(Exception e){
            logger.error("Could not create good hotel object.");
        }
        return goodHotelResult;
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
