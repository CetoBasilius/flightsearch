package com.basilio.flightsearch.entities;

import com.basilio.flightsearch.entities.result.Result;
import com.google.gson.Gson;
import org.apache.tapestry5.annotations.Log;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/5/12
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultCreator {
    private String resultString;

    public String getResultString() {
        return resultString;
    }

    @Log
    public Result getGoodResult(){
        Gson gson = new Gson();
        Result goodResult = null;
        try{
            goodResult = gson.fromJson(getAllResultString(),Result.class);
            System.out.println(goodResult);
        } catch(Exception e){
            e.printStackTrace();
        }
        return goodResult;
    }

    public String getAllResultString(){

        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

}
