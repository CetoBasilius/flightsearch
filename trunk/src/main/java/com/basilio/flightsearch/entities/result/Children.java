package com.basilio.flightsearch.entities.result;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */


public class Children{
    private Number baseFare;
    private Number quantity;

    public Number getBaseFare(){
        return this.baseFare;
    }
    public void setBaseFare(Number baseFare){
        this.baseFare = baseFare;
    }
    public Number getQuantity(){
        return this.quantity;
    }
    public void setQuantity(Number quantity){
        this.quantity = quantity;
    }
}
