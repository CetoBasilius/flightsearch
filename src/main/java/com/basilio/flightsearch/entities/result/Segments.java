
package com.basilio.flightsearch.entities.result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:02 PM
 * Air flight route segment
 */

public class Segments{
   	private Arrival arrival;
   	private String delayInfo;
   	private Departure departure;
   	private String duration;
   	private Number flightNumber;
   	private String marketingCabinTypeCode;
   	private String marketingCabinTypeDescription;
   	private String marketingCarrierCode;
   	private String marketingCarrierDescription;
   	private String operatingCarrierCode;
   	private String operatingCarrierDescription;
    private List<Stopovers> stopovers;

    private SimpleDateFormat hourFormat = new SimpleDateFormat("H:mm");

    public Arrival getArrival(){
		return this.arrival;
	}
	public void setArrival(Arrival arrival){
		this.arrival = arrival;
	}
 	public String getDelayInfo(){
		return this.delayInfo;
	}
	public void setDelayInfo(String delayInfo){
		this.delayInfo = delayInfo;
	}
 	public Departure getDeparture(){
		return this.departure;
	}
	public void setDeparture(Departure departure){
		this.departure = departure;
	}
 	public String getDuration(){
		return this.duration;
	}
	public void setDuration(String duration){
		this.duration = duration;
	}
 	public Number getFlightNumber(){
		return this.flightNumber;
	}
	public void setFlightNumber(Number flightNumber){
		this.flightNumber = flightNumber;
	}
 	public String getMarketingCabinTypeCode(){
		return this.marketingCabinTypeCode;
	}
	public void setMarketingCabinTypeCode(String marketingCabinTypeCode){
		this.marketingCabinTypeCode = marketingCabinTypeCode;
	}
 	public String getMarketingCabinTypeDescription(){
		return this.marketingCabinTypeDescription;
	}
	public void setMarketingCabinTypeDescription(String marketingCabinTypeDescription){
		this.marketingCabinTypeDescription = marketingCabinTypeDescription;
	}
 	public String getMarketingCarrierCode(){
		return this.marketingCarrierCode;
	}
	public void setMarketingCarrierCode(String marketingCarrierCode){
		this.marketingCarrierCode = marketingCarrierCode;
	}
 	public String getMarketingCarrierDescription(){
		return this.marketingCarrierDescription;
	}
	public void setMarketingCarrierDescription(String marketingCarrierDescription){
		this.marketingCarrierDescription = marketingCarrierDescription;
	}
 	public String getOperatingCarrierCode(){
		return this.operatingCarrierCode;
	}
	public void setOperatingCarrierCode(String operatingCarrierCode){
		this.operatingCarrierCode = operatingCarrierCode;
	}
 	public String getOperatingCarrierDescription(){
		return this.operatingCarrierDescription;
	}
	public void setOperatingCarrierDescription(String operatingCarrierDescription){
		this.operatingCarrierDescription = operatingCarrierDescription;
	}
 	public List<Stopovers> getStopovers(){
		return this.stopovers;
	}
	public void setStopovers(List<Stopovers> stopovers){
		this.stopovers = stopovers;
	}

    public String getDepartureDescription(){
        StringBuffer buffer = new StringBuffer();
        Departure departure1 = this.getDeparture();
        buffer.append(departure1.getLocation());
        buffer.append(", ");
        buffer.append(departure1.getLocationDescription());
        return buffer.toString();
    }

    public String getArrivalDescription(){
        StringBuffer buffer = new StringBuffer();
        Arrival arrival1 = this.getArrival();
        buffer.append(arrival1.getLocation());
        buffer.append(", ");
        buffer.append(arrival1.getLocationDescription());
        return buffer.toString();
    }

    public String getDescription() {
        StringBuffer buffer = new StringBuffer();
        return buffer.toString();
    }

    public String getStopDescription() {
        StringBuffer buffer = new StringBuffer();
        if(this.getStopovers()!=null){
            if(this.getStopovers().size()>0){
                buffer.append("The flight will stop at ");
                Stopovers stopovers1 = this.getStopovers().get(0);
                buffer.append(stopovers1.getAirportCode());
                buffer.append(", ");
                buffer.append(stopovers1.getAirportDescription());
                buffer.append(" for ");
                buffer.append(stopovers1.getDuration());
                buffer.append(" minutes.");
            }
        }

        return buffer.toString();
    }

    public String getDurationDescription() {
        StringBuffer buffer = new StringBuffer();
        Date duration = new Date();
        try {
            duration = hourFormat.parse(this.getDuration());
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        buffer.append(duration.getHours());
        buffer.append(" hours, ");
        buffer.append(duration.getMinutes());
        buffer.append(" minutes.");
        return buffer.toString();
    }
}
