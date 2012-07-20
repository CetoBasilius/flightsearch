
package com.basilio.flightsearch.entities.result;

import java.util.ArrayList;
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
   	private List<InboundRoutes> inboundRoutes;
   	private List<ItineraryInfos> itineraryInfos;
   	private List<OutboundRoutes> outboundRoutes;
   	private PaymentInfo paymentInfo;
   	private PriceInfo priceInfo;
    private boolean returnDirectFlights;


    public void Flights(){
        this.id = "empty";
        this.inboundRoutes = new ArrayList<InboundRoutes>();
        this.itineraryInfos = new ArrayList<ItineraryInfos>();
        this.outboundRoutes = new ArrayList<OutboundRoutes>();
        this.paymentInfo = new PaymentInfo();
        this.priceInfo = new PriceInfo();
        this.returnDirectFlights = false;
    }

    public boolean isReturnDirectFlights() {
        return returnDirectFlights;
    }

    public void setReturnDirectFlights(boolean returnDirectFlights) {
        this.returnDirectFlights = returnDirectFlights;
    }

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
 	public List<ItineraryInfos> getItineraryInfos(){
		return this.itineraryInfos;
	}
	public void setItineraryInfos(List itineraryInfos){
		this.itineraryInfos = itineraryInfos;
	}
 	public List<OutboundRoutes> getOutboundRoutes(){
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

    public String getDescription() {
        return "This flight is from somewhere to somewhere, on some date, the following are available:";
    }
}
