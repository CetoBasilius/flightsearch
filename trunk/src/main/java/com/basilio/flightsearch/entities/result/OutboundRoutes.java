
package com.basilio.flightsearch.entities.result;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 1:02 PM
 * Outbound routes, duration and segments of total route.
 */

public class OutboundRoutes{

    private static final Logger logger = LoggerFactory.getLogger(OutboundRoutes.class);

   	private String duration;
   	private boolean hasAirportChange;
   	private List<Segments> segments;
   	private String type;

    private SimpleDateFormat inDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat outDateFormat = new SimpleDateFormat("MMMM d', 'H:mm");
    private SimpleDateFormat inHourFormat = new SimpleDateFormat("H:mm");
    private SimpleDateFormat outHourFormat = new SimpleDateFormat("H' hours and 'mm' minutes'");

    public String getDuration(){
		return this.duration;
	}
	public void setDuration(String duration){
		this.duration = duration;
	}
 	public boolean getHasAirportChange(){
		return this.hasAirportChange;
	}
	public void setHasAirportChange(boolean hasAirportChange){
		this.hasAirportChange = hasAirportChange;
	}
 	public List<Segments> getSegments(){
		return this.segments;
	}
	public void setSegments(List<Segments> segments){
		this.segments = segments;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}

    public String getFinalDestination(){
        Arrival arrival = this.getSegments().get(this.getSegments().size() - 1).getArrival();
        String returnString = arrival.getLocation()+", "+arrival.getLocationDescription();
        return returnString;
    }

    public String getDeparturePlace(){
        Departure departure = this.getSegments().get(0).getDeparture();
        String returnString = departure.getLocation()+", "+departure.getLocationDescription();
        return returnString;
    }

    public String getDepartureTime(){
        return this.getSegments().get(0).getDeparture().getDate();
    }

    public String getDescription() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("This flight leaves from ");
        buffer.append(this.getDeparturePlace());
        buffer.append(", and will arrive at ");
        buffer.append(this.getFinalDestination());
        return buffer.toString();
    }

    public String getScheduleDescription() {
        StringBuffer buffer = new StringBuffer();
        Date departureDate = new Date();
        Date duration = new Date();
        try {
            departureDate = inDateFormat.parse(this.getDepartureTime());
            duration = inHourFormat.parse(this.getDuration());
        } catch (ParseException e) {
            logger.error("Date was unparseable!");
        }

        Date arriveDate;
        arriveDate = DateUtils.addHours(departureDate,duration.getHours());
        arriveDate = DateUtils.addMinutes(arriveDate, duration.getMinutes());

        buffer.append("Leaves at ");
        buffer.append(outDateFormat.format(departureDate));
        buffer.append(", arrives at ");
        buffer.append(outDateFormat.format(arriveDate));

        return buffer.toString();
    }

    public String getDurationDescription() {
        StringBuffer buffer = new StringBuffer();
        Date departureDate = new Date();
        Date duration = new Date();
        try {
            departureDate = inDateFormat.parse(this.getDepartureTime());
            duration = inHourFormat.parse(this.getDuration());
        } catch (ParseException e) {
            logger.error("Date was unparseable!");
        }

        buffer.append(outHourFormat.format(duration));

        return buffer.toString();
    }

    public String getSegmentsDescription() {
        StringBuffer buffer = new StringBuffer();
        if(this.getSegments().size()>1){
            buffer.append(this.getSegments().size());
        } else {
            buffer.append(" Flight is direct.");
        }

        return buffer.toString();
    }
}
