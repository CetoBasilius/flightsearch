
package com.basilio.flightsearch.entities.result;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 11:22 AM
 * Actual flight result.
 */
public class Flight {
   	private String id;
   	private List<Route> inboundRoutes;
   	private List<ItineraryInfos> itineraryInfos;
   	private List<Route> outboundRoutes;
   	private PaymentInfo paymentInfo;
   	private PriceInfo priceInfo;
    private boolean returnDirectFlights;

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
 	public List<Route> getInboundRoutes(){
		return this.inboundRoutes;
	}
	public void setInboundRoutes(List<Route> inboundRoutes){
		this.inboundRoutes = inboundRoutes;
	}
 	public List<ItineraryInfos> getItineraryInfos(){
		return this.itineraryInfos;
	}
	public void setItineraryInfos(List<ItineraryInfos> itineraryInfos){
		this.itineraryInfos = itineraryInfos;
	}
 	public List<Route> getOutboundRoutes(){
		return this.outboundRoutes;
	}
	public void setOutboundRoutes(List<Route> outboundRoutes){
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
        return "";
    }

    public String getOutDescription() {
        StringBuffer buffer = new StringBuffer();
        if(outboundRoutes!=null){
            if(outboundRoutes.size()>0){
                buffer.append(outboundRoutes.get(0).getDescription());
            }else{
                buffer.append("Outbound routes was empty");
            }
        }else{
            buffer.append("Outbound routes was null");
        }
        return buffer.toString();
    }

    public String getInDescription() {
        StringBuffer buffer = new StringBuffer();
        if(inboundRoutes!=null){
            if(inboundRoutes.size()>0){
                buffer.append(inboundRoutes.get(0).getDescription());
            }else{
                buffer.append("Inbound routes was empty");
            }
        }else{
            buffer.append("Inbound routes was null");
        }
        return buffer.toString();
    }
}
