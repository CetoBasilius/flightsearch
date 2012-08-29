package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.PaymentOption;
import com.basilio.flightsearch.entities.flightresult.Flight;
import com.basilio.flightsearch.entities.flightresult.FlightSearch;
import com.basilio.flightsearch.entities.Passenger;
import com.basilio.flightsearch.entities.flightresult.Payment;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.*;
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

    @Persist
    private List<Passenger> passengerList;
    @Persist
    private List<PaymentOption> paymentOptionList;

    @Persist
    @Property
    private int passengerIndex;

    @Persist
    @Property
    private String lastName;
    @Persist
    @Property
    private String birthDay;
    @Persist
    @Property
    private String birthMonth;
    @Persist
    @Property
    private String birthYear;
    @Persist
    @Property
    private String gender;
    @Persist
    @Property
    private boolean agreeTerms;
    @Persist
    @Property
    private String email;
    @Persist
    @Property
    private String confirmEmail;
    @Persist
    @Property
    private String phone;
    @Persist
    @Property
    private Passenger passenger;
    @Persist
    @Property
    private PaymentOption paymentOption;
    @Persist
    @Property
    private int paymentIndex;
    @Persist
    @Property
    private String paymentRadioSelectedValue;
    @Persist
    @Property
    private String cardType;
    @Persist
    @Property
    private String expireMonth;
    @Persist
    @Property
    private String expireYear;
    @Persist
    @Property
    private String cardNumber;
    @Persist
    @Property
    private String cardCode;
    @Persist
    @Property
    private String cardOwner;


    @Persist
    private String builtFinalMessage;
    @Persist
    private FlightSearch flightSearch;
    @Persist
    private Flight flight;
    @Persist
    private FlightSearch search;

    public String getFinalMessage(){
        return builtFinalMessage;
    }

    public Passenger[] getPassengers(){
        return passengerList.toArray(new Passenger[0]);
    }

    public PaymentOption[] getPaymentOptions(){
        return paymentOptionList.toArray(new PaymentOption[0]);
    }

    @Property
    private final ValueEncoder<Passenger> passengerValueEncoder = new ValueEncoder<Passenger>() {

        public String toClient(Passenger answer) {
            int in = passengerList.indexOf(answer);
            return String.valueOf(in);
        }

        public Passenger toValue(String str) {
            int index = Integer.parseInt(str);
            return passengerList.get(index);
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
        builtFinalMessage = "";
        if(passengerList != null){
            System.out.println(passengerList.toString());
            for(Passenger passenger : passengerList){
                builtFinalMessage += passenger.getName();
            }
        }

        return null;

    }

    public String getPaymentRadio() {
        //TODO: this method gives me the correct value
        return String.valueOf(paymentIndex);
    }

    public String getPaymentDescription(){
        return paymentOptionList.get(paymentIndex).getDescription();
    }

    public void setup(FlightSearch search, Flight flight) {
        this.search = search;
        if(passengerList == null){
            passengerList = new ArrayList<Passenger>();
        }
        if(paymentOptionList == null){
            paymentOptionList = new ArrayList<PaymentOption>();
        }
        for(int index = 0; index < search.getNumberAdults();index++){
            passengerList.add(new Passenger(Passenger.TYPE_ADULT));
        }
        for(int index = 0; index < search.getNumberChildren();index++){
            passengerList.add(new Passenger(Passenger.TYPE_CHILD));
        }
        for(int index = 0; index < search.getNewBorns();index++){
            passengerList.add(new Passenger(Passenger.TYPE_INFANT));
        }

        this.flight = flight;
        List<Payment> payments = flight.getPaymentInfo().getPayments();
        for(int index = 0; index < payments.size();index++){
            PaymentOption paymentOption = new PaymentOption();
            paymentOption.setDescription(payments.get(index).getInstallments().getQuantity()+" payments of "+payments.get(index).getInstallments().getOthers());
            paymentOptionList.add(paymentOption);
        }
    }

    public boolean getPageReady(){
        return passengerList != null;
    }
}
