
package com.basilio.flightsearch.entities.result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */

public class Arrival{
   	private String date;
   	private String location;
   	private String locationDescription;
   	private Number timezone;

 	public String getDate(){
		return this.date;
	}
	public void setDate(String date){
		this.date = date;
	}
 	public String getLocation(){
		return this.location;
	}
	public void setLocation(String location){
		this.location = location;
	}
 	public String getLocationDescription(){
		return this.locationDescription;
	}
	public void setLocationDescription(String locationDescription){
		this.locationDescription = locationDescription;
	}
 	public Number getTimezone(){
		return this.timezone;
	}
	public void setTimezone(Number timezone){
		this.timezone = timezone;
	}
}
