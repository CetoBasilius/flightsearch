
package com.basilio.flightsearch.entities.result;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 11:22 AM
 * Actual flight result.
 */
public class Flights{
   	private String id;
   	private List inboundRoutes;
   	private List<ItineraryInfos> itineraryInfos;
   	private List<OutboundRoutes> outboundRoutes;
   	private PaymentInfo paymentInfo;
   	private PriceInfo priceInfo;

 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public List getInboundRoutes(){
		return this.inboundRoutes;
	}
	public void setInboundRoutes(List inboundRoutes){
		this.inboundRoutes = inboundRoutes;
	}
 	public List getItineraryInfos(){
		return this.itineraryInfos;
	}
	public void setItineraryInfos(List itineraryInfos){
		this.itineraryInfos = itineraryInfos;
	}
 	public List getOutboundRoutes(){
		return this.outboundRoutes;
	}
	public void setOutboundRoutes(List outboundRoutes){
		this.outboundRoutes = outboundRoutes;
	}
 	public PaymentInfo getPaymentInfo(){
		return this.paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo){
		this.paymentInfo = paymentInfo;
	}
 	public PriceInfo getPriceInfo(){
		return this.priceInfo;
	}
	public void setPriceInfo(PriceInfo priceInfo){
		this.priceInfo = priceInfo;
	}
}
