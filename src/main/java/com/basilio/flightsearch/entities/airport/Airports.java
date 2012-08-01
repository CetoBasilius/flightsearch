
package com.basilio.flightsearch.entities.airport;

public class Airports{
   	private String description;
   	private GeoLocation geoLocation;
   	private String id;
   	private String internalId;
   	private String parentCity;

 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public GeoLocation getGeoLocation(){
		return this.geoLocation;
	}
	public void setGeoLocation(GeoLocation geoLocation){
		this.geoLocation = geoLocation;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getInternalId(){
		return this.internalId;
	}
	public void setInternalId(String internalId){
		this.internalId = internalId;
	}
 	public String getParentCity(){
		return this.parentCity;
	}
	public void setParentCity(String parentCity){
		this.parentCity = parentCity;
	}
}
