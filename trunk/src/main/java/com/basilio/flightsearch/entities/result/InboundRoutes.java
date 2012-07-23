
package com.basilio.flightsearch.entities.result;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InboundRoutes{

    private static final Logger logger = LoggerFactory.getLogger(InboundRoutes.class);

   	private String duration;
   	private boolean hasAirportChange;
   	private List<Segments> segments;
   	private String type;

    private SimpleDateFormat inDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat outDateFormat = new SimpleDateFormat("MMMM d', 'H:mm");
    private SimpleDateFormat inHourFormat = new SimpleDateFormat("H:mm");
    private SimpleDateFormat outHourFormat = new SimpleDateFormat("H' hours and 'mm' ,minutes'");

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
	public void setSegments(List segments){
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

        Date departureDate = new Date();
        Date duration = new Date();
        try {
            departureDate = inDateFormat.parse(this.getDepartureTime());
            duration = inHourFormat.parse(this.getDuration());
        } catch (ParseException e) {
            logger.error("Date was unparseable!");
        }

        Date arriveDate;
        arriveDate = DateUtils.addHours(departureDate, duration.getHours());
        arriveDate = DateUtils.addMinutes(arriveDate, duration.getMinutes());

        StringBuffer buffer = new StringBuffer();
        buffer.append("This flight leaves at ");
        buffer.append(outDateFormat.format(departureDate));
        buffer.append(", from ");
        buffer.append(this.getDeparturePlace());
        buffer.append(", arrives on ");
        buffer.append(outDateFormat.format(arriveDate));
        buffer.append(", at ");
        buffer.append(this.getFinalDestination());
        buffer.append(", it has a duration of ");
        buffer.append(outHourFormat.format(duration));
        buffer.append(". Segment size: ");
        buffer.append(this.getSegments().size());

        return buffer.toString();
    }

}
