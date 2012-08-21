package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.flightresult.FlightSearch;
import com.basilio.flightsearch.entities.Passenger;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/6/12
 * Time: 5:41 PM
 * Page where user confirms payment and passenger information
 */

@GuestAccess
public class ConfirmPage {

    @Component
    private Form confirmForm;

    private List<Passenger> passengerList;

    public Passenger[] getPassengers(){
        return passengerList.toArray(new Passenger[0]);
    }

    public void setupRender(){
        if (passengerList == null) {
            passengerList = new ArrayList<Passenger>();
            Passenger passenger = new Passenger(Passenger.TYPE_ADULT);
            passengerList.add(passenger);
        }
    }

    @Property
    private Passenger passenger;

    private FlightSearch flightSearch;

    @Property
    private final ValueEncoder<Passenger> passengerValueEncoder = new ValueEncoder<Passenger>() {

        public String toClient(Passenger answer) {
            int in = passengerList.indexOf(answer);
            return String.valueOf(answer.getPassengerType());
        }

        public Passenger toValue(String str) {
            return null;
        }
    };

    public String getPassengerId(){
        return passenger.getPassengerTypeString();
    }

    @OnEvent(value = "confirmPurchase")
    public Object confirmPurchase(){
        return null;
    }


}
