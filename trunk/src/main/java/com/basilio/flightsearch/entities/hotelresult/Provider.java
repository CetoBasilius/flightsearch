
package com.basilio.flightsearch.entities.hotelresult;

import java.util.List;

public class Provider{
   	private String hotelId;
   	private String id;
   	private String subProviderId;

 	public String getHotelId(){
		return this.hotelId;
	}
	public void setHotelId(String hotelId){
		this.hotelId = hotelId;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getSubProviderId(){
		return this.subProviderId;
	}
	public void setSubProviderId(String subProviderId){
		this.subProviderId = subProviderId;
	}
}
