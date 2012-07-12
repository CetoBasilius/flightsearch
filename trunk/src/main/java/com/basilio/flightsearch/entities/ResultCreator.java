package com.basilio.flightsearch.entities;

import com.basilio.flightsearch.entities.result.Result;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/5/12
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultCreator {
    private List<String> result;
    private Result goodResult;

    public List<String> getResult() {
        return result;
    }

    public void createGoodResult(){
        Gson gson = new Gson();

        try{
            goodResult = gson.fromJson(getAllResultString(),Result.class);
            System.out.println(goodResult);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getAllResultString(){
        StringBuffer stringBuffer = new StringBuffer();

        for(String line : result){
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }

    public void setResult(List<String> result) {
        this.result = result;
    }


    public Result getGoodResult() {
        return goodResult;
    }

    public void setGoodResult(Result goodResult) {
        this.goodResult = goodResult;
    }

}
