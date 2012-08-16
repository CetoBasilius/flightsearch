package com.basilio.flightsearch.entities.flightresult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:02 PM
 * Air flight route segment
 */

public class Segment {

    private static final Logger logger = LoggerFactory.getLogger(Segment.class);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat outDateFormat = new SimpleDateFormat("H:mm', 'MMMM d");

    private Location arrival;
    private String delayInfo;
    private Location departure;
    private String duration;
    private Number flightNumber;
    private String marketingCabinTypeCode;
    private String marketingCabinTypeDescription;
    private String marketingCarrierCode;
    private String marketingCarrierDescription;
    private String operatingCarrierCode;
    private String operatingCarrierDescription;
    private List<Stopover> stopovers;

    private SimpleDateFormat hourFormat = new SimpleDateFormat("H:mm");

    public Segment() {
        arrival = new Location("");
        delayInfo = "";
        departure = new Location("");
        duration = "0";
        flightNumber = 0;
        marketingCabinTypeCode = "";
        marketingCabinTypeDescription = "";
        marketingCarrierCode = "";
        marketingCarrierDescription = "";
        operatingCarrierCode = "";
        operatingCarrierDescription = "";
        stopovers = new ArrayList<Stopover>();
    }

    public Location getArrival() {
        return this.arrival;
    }

    public void setArrival(Location arrival) {
        this.arrival = arrival;
    }

    public String getDelayInfo() {
        return this.delayInfo;
    }

    public void setDelayInfo(String delayInfo) {
        this.delayInfo = delayInfo;
    }

    public Location getDeparture() {
        return this.departure;
    }

    public void setDeparture(Location departure) {
        this.departure = departure;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Number getFlightNumber() {
        return this.flightNumber;
    }

    public void setFlightNumber(Number flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getMarketingCabinTypeCode() {
        return this.marketingCabinTypeCode;
    }

    public void setMarketingCabinTypeCode(String marketingCabinTypeCode) {
        this.marketingCabinTypeCode = marketingCabinTypeCode;
    }

    public String getMarketingCabinTypeDescription() {
        return this.marketingCabinTypeDescription;
    }

    public void setMarketingCabinTypeDescription(String marketingCabinTypeDescription) {
        this.marketingCabinTypeDescription = marketingCabinTypeDescription;
    }

    public String getMarketingCarrierCode() {
        return this.marketingCarrierCode;
    }

    public void setMarketingCarrierCode(String marketingCarrierCode) {
        this.marketingCarrierCode = marketingCarrierCode;
    }

    public String getMarketingCarrierDescription() {
        return this.marketingCarrierDescription;
    }

    public void setMarketingCarrierDescription(String marketingCarrierDescription) {
        this.marketingCarrierDescription = marketingCarrierDescription;
    }

    public String getOperatingCarrierCode() {
        return this.operatingCarrierCode;
    }

    public void setOperatingCarrierCode(String operatingCarrierCode) {
        this.operatingCarrierCode = operatingCarrierCode;
    }

    public String getOperatingCarrierDescription() {
        return this.operatingCarrierDescription;
    }

    public void setOperatingCarrierDescription(String operatingCarrierDescription) {
        this.operatingCarrierDescription = operatingCarrierDescription;
    }

    public List<Stopover> getStopovers() {
        return this.stopovers;
    }

    public void setStopovers(List<Stopover> stopovers) {
        this.stopovers = stopovers;
    }

    public String getDepartureDescription() {
        StringBuffer buffer = new StringBuffer();
        Location departure1 = this.getDeparture();
        buffer.append(departure1.getLocation());
        buffer.append(", ");
        buffer.append(departure1.getLocationDescription());
        return buffer.toString();
    }

    public String getArrivalDescription() {
        StringBuffer buffer = new StringBuffer();
        Location arrival1 = this.getArrival();
        buffer.append(arrival1.getLocation());
        buffer.append(", ");
        buffer.append(arrival1.getLocationDescription());
        return buffer.toString();
    }

    public String getDescription() {
        return "";
    }

    public String getStopDescription() {
        StringBuffer buffer = new StringBuffer();
        if (this.getStopovers() != null) {
            if (this.getStopovers().size() > 0) {
                buffer.append("The flight will stop at ");
                Stopover stopovers1 = this.getStopovers().get(0);
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

    public String getDepartureHourDescription() {
        Date departureHour = new Date();
        try {
            departureHour = dateFormat.parse(this.getDeparture().getDate());
        } catch (ParseException e) {
            logger.error("departure date was unparseable!");
        }
        return "at " + outDateFormat.format(departureHour);
    }

    public String getArrivalHourDescription() {
        Date arriveHour = new Date();
        try {
            arriveHour = dateFormat.parse(this.getArrival().getDate());
        } catch (ParseException e) {
            logger.error("arrive date was unparseable!");
        }
        return "at " + outDateFormat.format(arriveHour);
    }
}
