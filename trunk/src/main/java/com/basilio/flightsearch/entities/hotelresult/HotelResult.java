
package com.basilio.flightsearch.entities.hotelresult;

import java.util.List;

public class HotelResult{
   	private List<Hotel> availability;
   	private Meta meta;

 	public List<Hotel> getAvailability(){
		return this.availability;
	}
	public void setAvailability(List<Hotel> availability){
		this.availability = availability;
	}
 	public Meta getMeta(){
		return this.meta;
	}
	public void setMeta(Meta meta){
		this.meta = meta;
	}
}
