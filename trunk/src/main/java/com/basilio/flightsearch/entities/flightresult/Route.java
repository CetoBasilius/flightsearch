package com.basilio.flightsearch.entities.flightresult;

import com.basilio.flightsearch.core.helpers.DateHelper;
import org.apache.commons.lang.time.DateUtils;
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
 * Date: 7/24/12
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Route {

    private static final Logger logger = LoggerFactory.getLogger(Route.class);

    private String duration;
    private boolean hasAirportChange;
    private List<Segment> segments;
    private String type;

    private SimpleDateFormat inDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat outDateFormat = new SimpleDateFormat("MMMM d', 'H:mm");
    private SimpleDateFormat inHourFormat = new SimpleDateFormat("H:mm");
    private SimpleDateFormat outHourFormat = new SimpleDateFormat("H' hours and 'mm' minutes'");

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
        arriveDate = DateUtils.addHours(departureDate, duration.getHours());
        arriveDate = DateUtils.addMinutes(arriveDate, duration.getMinutes());

        buffer.append("Leaves at ");
        buffer.append(outDateFormat.format(departureDate));
        buffer.append(", arrives at ");
        buffer.append(outDateFormat.format(arriveDate));

        return buffer.toString();
    }

    public String getDurationDescription() {
        StringBuffer buffer = new StringBuffer();
        Date duration = new Date();
        try {
            duration = inHourFormat.parse(this.getDuration());
        } catch (ParseException e) {
            logger.error("Date was unparseable!");
        }

        buffer.append(outHourFormat.format(duration));

        return buffer.toString();
    }

    public String getLeaveDescription() {
        return this.getDeparturePlace();
    }

    public String getArriveDescription() {
        return this.getFinalDestination();
    }

    public String getSegmentsNumber() {
        StringBuffer buffer = new StringBuffer();
        if (this.getSegments().size() > 1) {
            buffer.append(this.getSegments().size());
            buffer.append(" segments");
        } else {
            buffer.append(" Direct");
        }

        return buffer.toString();
    }

    public String[] getSegmentsDescription() {
        int size = this.getSegments().size();
        String[] segmentsDesc = new String[size + 1];
        for (int index = 0; index < size; index++) {
            Segment segments1 = this.getSegments().get(index);
            Location departure = segments1.getDeparture();
            segmentsDesc[index] = departure.getLocation();
        }
        segmentsDesc[size] = this.getSegments().get(size - 1).getArrival().getLocation();
        return segmentsDesc;
    }

    public String getWaitDescription(int segmentIndex1, int segmentIndex2) {
        StringBuffer buffer = new StringBuffer();

        Segment segment1 = this.getSegments().get(segmentIndex1);
        Segment segment2 = this.getSegments().get(segmentIndex2);

        Date date1 = new Date();
        Date date2 = new Date();

        Date dateDifference = new Date();

        try {
            date1 = inDateFormat.parse(segment1.getArrival().getDate());
            date2 = inDateFormat.parse(segment2.getDeparture().getDate());
            logger.info(date1.toString() + "," + date2.toString());
        } catch (ParseException e) {
            logger.error("date was unparseable.");
        }
        dateDifference.setTime(date2.getTime() - date1.getTime());
        String returnString = DateHelper.getHoursMinutes(dateDifference);
        return returnString;

    }

    public String getDescription() {
        return "from " + this.getLeaveDescription() + " to " + this.getArriveDescription();
    }

    public Route() {
        this.segments = new ArrayList<Segment>();
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean getHasAirportChange() {
        return this.hasAirportChange;
    }

    public void setHasAirportChange(boolean hasAirportChange) {
        this.hasAirportChange = hasAirportChange;
    }

    public List<Segment> getSegments() {
        return this.segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFinalDestination() {
        Location arrival = this.getSegments().get(this.getSegments().size() - 1).getArrival();
        return arrival.getLocation();
    }

    public String getDeparturePlace() {
        Location departure = this.getSegments().get(0).getDeparture();
        return departure.getLocation();
    }

    public String getDepartureTime() {
        return this.getSegments().get(0).getDeparture().getDate();
    }
}
