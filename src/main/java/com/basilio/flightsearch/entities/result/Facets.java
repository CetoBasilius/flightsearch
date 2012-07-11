
package com.basilio.flightsearch.entities.result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 10:21 AM
 * Stores airlines, stops, and price ranges.
 */

public class Facets{
   	private String key;
   	private String subType;
   	private String type;
   	private List<Values> values;
    private Number min;
    private Number max;

 	public String getKey(){
		return this.key;
	}
	public void setKey(String key){
		this.key = key;
	}
 	public String getSubType(){
		return this.subType;
	}
	public void setSubType(String subType){
		this.subType = subType;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
 	public List getValues(){
		return this.values;
	}
	public void setValues(List<Values> values){
		this.values = values;
	}

    public Number getMax() {
        return max;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(Number min) {
        this.min = min;
    }
}
