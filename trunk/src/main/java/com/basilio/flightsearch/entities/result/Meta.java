
package com.basilio.flightsearch.entities.result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 10:12 AM
 * Basic search result information.
 */

public class Meta{
   	private String currencyCode;
   	private List<Facets> facets;
   	private Number pageCount;
   	private String reference;
   	private String ticket;
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
 	public Number getPageCount(){
		return this.pageCount;
	}
	public void setPageCount(Number pageCount){
		this.pageCount = pageCount;
	}
 	public String getReference(){
		return this.reference;
	}
	public void setReference(String reference){
		this.reference = reference;
	}
 	public String getTicket(){
		return this.ticket;
	}
	public void setTicket(String ticket){
		this.ticket = ticket;
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
