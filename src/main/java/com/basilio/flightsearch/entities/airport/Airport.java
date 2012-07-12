
package com.basilio.flightsearch.entities.airport;

import java.util.List;

public class Airport{
   	private List airports;
   	private Meta meta;

 	public List getAirports(){
		return this.airports;
	}
	public void setAirports(List airports){
		this.airports = airports;
	}
 	public Meta getMeta(){
		return this.meta;
	}
	public void setMeta(Meta meta){
		this.meta = meta;
	}
}
