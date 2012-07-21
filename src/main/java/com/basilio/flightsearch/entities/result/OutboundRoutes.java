
package com.basilio.flightsearch.entities.result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 1:02 PM
 * Outbound routes, duration and segments of total route.
 */

public class OutboundRoutes{
   	private String duration;
   	private boolean hasAirportChange;
   	private List<Segments> segments;
   	private String type;

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
        buffer.append("This flight leaves at: ");
        buffer.append(this.getDepartureTime());
        buffer.append(", it has a duration of ");
        buffer.append(this.getDuration());
        buffer.append(". Segment size: ");
        buffer.append(this.getSegments().size());
        buffer.append(". Leaves from ");
        buffer.append(this.getDeparturePlace());
        buffer.append(", arrives at ");
        buffer.append(this.getFinalDestination());

        return buffer.toString();
    }
}
