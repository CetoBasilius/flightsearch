package com.basilio.flightsearch.dal.hotel;

import com.basilio.flightsearch.entities.hotelresult.HotelResult;
import com.basilio.flightsearch.entities.hotelresult.HotelSearch;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/3/12
 * Time: 8:19 AM
 * To change this template use File | Settings | File Templates.
 */
public interface HotelSearchConnector {

    public HotelResult hotelSearch(HotelSearch hotelSearch);
}
