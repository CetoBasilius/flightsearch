
package com.basilio.flightsearch.entities.hotelresult;

import java.util.List;

public class Meta{
   	private String currencyCode;
   	private List<Facets> facets;
   	private String reference;
   	private String time;
   	private Number total;

 	public String getCurrencyCode(){
		return this.currencyCode;
	}
	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}
 	public List<Facets> getFacets(){
		return this.facets;
	}
	public void setFacets(List<Facets> facets){
		this.facets = facets;
	}
 	public String getReference(){
		return this.reference;
	}
	public void setReference(String reference){
		this.reference = reference;
	}
 	public String getTime(){
		return this.time;
	}
	public void setTime(String time){
		this.time = time;
	}
 	public Number getTotal(){
		return this.total;
	}
	public void setTotal(Number total){
		this.total = total;
	}
}
