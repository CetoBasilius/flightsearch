package com.basilio.flightsearch.pages;


import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.hotel.HotelSearchConnector;
import com.basilio.flightsearch.dal.persistence.ServiceDAO;
import com.basilio.flightsearch.entities.flightresult.Route;
import com.basilio.flightsearch.entities.hotelresult.Hotel;
import com.basilio.flightsearch.entities.hotelresult.HotelResult;
import com.basilio.flightsearch.entities.hotelresult.HotelSearch;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.chenillekit.tapestry.core.components.DateTimeField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/26/12
 * Time: 4:48 AM
 * This is just a test page. Future enhancements are tested here.
 */

@GuestAccess
public class Tester {

    @Inject
    private HotelSearchConnector hotelSearchConnector;

    @InjectComponent
    private Form testForm;

    @Property
    @Persist
    private String city;

    @Property
    private Hotel hotel;

    @Property
    @Persist
    private List<Hotel> hotels;

    @Property
    @Persist
    private Date inDate;

    @Property
    @Persist
    private Date outDate;

    @Component
    private DateTimeField endDateTimeField;

    @Component
    private DateTimeField startDateTimeField;


    @Log
    @OnEvent(value = EventConstants.SUCCESS, component = "testForm")
    public Object searchHotelsOnCity(){
        HotelSearch hotelSearch = new HotelSearch();


        hotelSearch.setCheckInDate(inDate);
        hotelSearch.setCheckOutDate(outDate);
        hotelSearch.setDistribution("1");
        hotelSearch.setCityCode(city);

        HotelResult hotelResult = hotelSearchConnector.hotelSearch(hotelSearch);

        hotels = hotelResult.getAvailability();

        return null;
    }


    public Object onActionFromTester(){

        return null;
    }

    @Property
    private final ValueEncoder<Hotel> hotelValueEncoder = new ValueEncoder<Hotel>() {

        public String toClient(Hotel answer) {
            return hotel.getProvider().getHotelId();
        }

        public Hotel toValue(String str) {
            return null;
        }
    };

    public String getHotelName(){
        return hotel.getProvider().getId();
    }


}
