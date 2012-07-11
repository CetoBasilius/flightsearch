
package com.basilio.flightsearch.entities.result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:49 PM
 * Base result class
 */

public class Result{
   	private List<Flights> flights;
   	private Meta meta;

 	public List getFlights(){
		return this.flights;
	}
	public void setFlights(List<Flights> flights){
		this.flights = flights;
	}
 	public Meta getMeta(){
		return this.meta;
	}
	public void setMeta(Meta meta){
		this.meta = meta;
	}
}
