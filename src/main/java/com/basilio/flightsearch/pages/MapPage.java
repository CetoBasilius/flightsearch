package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/27/12
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
@GuestAccess
public class MapPage {

    @Property
    private double latin = 0;

    @Property
    private double lngin = 0;

    @Persist(PersistenceConstants.SESSION)
    @Property
    private List<Double> coordinatesParameter;

    @Persist(PersistenceConstants.SESSION)
    @Property
    private String visualizingFlight;

    public void setupGMap(List<Double> coordinatesin){
        coordinatesParameter = coordinatesin;
    }
}
