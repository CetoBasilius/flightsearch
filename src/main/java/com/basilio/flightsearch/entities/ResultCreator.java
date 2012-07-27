package com.basilio.flightsearch.entities;

import com.basilio.flightsearch.entities.result.Result;
import com.google.gson.Gson;
import org.apache.tapestry5.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/5/12
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultCreator {

    private static final Logger logger = LoggerFactory.getLogger(ResultCreator.class);
    private String resultString;
    private Gson gson;

    public ResultCreator() {
        gson = new Gson();
    }

    public String getResultString() {
        return resultString;
    }

    @Log
    public Result getGoodResult(){
        Result goodResult = null;
        try{
            goodResult = gson.fromJson(getAllResultString(),Result.class);
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
