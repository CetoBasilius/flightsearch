package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.PaymentOption;
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
    private List<PaymentOption> paymentOptionList;

    @Property
    private int passengerIndex;
    @Property
    private String passengerName;
    @Property
    private String lastName;
    @Property
    private String birthDay;
    @Property
    private String birthMonth;
    @Property
    private String birthYear;
    @Property
    private String gender;
    @Property
    private boolean agreeTerms;
    @Property
    private String email;
    @Property
    private String confirmEmail;
    @Property
    private String phone;
    @Property
    private Passenger passenger;
    @Property
    private PaymentOption paymentOption;
    @Property
    private int paymentIndex;
    @Property
    private String paymentRadioSelectedValue;

    private FlightSearch flightSearch;

    public Passenger[] getPassengers(){
        return passengerList.toArray(new Passenger[0]);
    }

    public PaymentOption[] getPaymentOptions(){
        return paymentOptionList.toArray(new PaymentOption[0]);
    }

    public void setupRender(){
        if (passengerList == null) {
            paymentOptionList = new ArrayList<PaymentOption>();
            PaymentOption paymentOption = new PaymentOption();
            paymentOptionList.add(paymentOption);

            passengerList = new ArrayList<Passenger>();
            Passenger passenger1 = new Passenger(Passenger.TYPE_ADULT);
            Passenger passenger2 = new Passenger(Passenger.TYPE_CHILD);
            passengerList.add(passenger1);
            passengerList.add(passenger2);
        }
    }

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

    @Property
    private final ValueEncoder<PaymentOption> paymentValueEncoder = new ValueEncoder<PaymentOption>() {

        public String toClient(PaymentOption answer) {
            int in = paymentOptionList.indexOf(answer);
            return String.valueOf(answer.toString());
        }

        public PaymentOption toValue(String str) {
            return null;
        }
    };

    private int numAdult;
    private int numChild;
    private int numInfant;

    public String getPassengerId(){
        switch (passenger.getPassengerType()){
            case(Passenger.TYPE_ADULT):{
                numAdult++;
                return passenger.getPassengerTypeString()+numAdult;
            }
            case(Passenger.TYPE_CHILD):{
                numChild++;
                return passenger.getPassengerTypeString()+numChild;
            }
            case(Passenger.TYPE_INFANT):{
                numInfant++;
                return passenger.getPassengerTypeString()+numInfant;
            }
        }
        return passenger.getPassengerTypeString();
    }

    @OnEvent(value = "confirmPurchase")
    public Object confirmPurchase(){
        return null;

    }

}
