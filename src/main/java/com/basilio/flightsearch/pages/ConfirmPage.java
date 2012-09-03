package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.Window;
import com.basilio.flightsearch.core.helpers.NumberHelper;
import com.basilio.flightsearch.entities.PaymentOption;
import com.basilio.flightsearch.entities.flightresult.*;
import com.basilio.flightsearch.entities.Passenger;
import org.apache.commons.lang.WordUtils;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

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

    @Property
    @Inject
    private Request request;
    @Property
    private int outSegmentWindowIndex;
    @Property
    private int inSegmentWindowIndex;

    @Component(parameters = {"style=greylighting",
            "show=false",
            "modal=true",
            "width=500",
            "title=literal:Outbound flight details"})
    private Window outboundWindow;

    @Component(parameters = {"style=greylighting",
            "show=false",
            "modal=true",
            "width=500",
            "title=literal:Inbound flight details"})
    private Window inboundWindow;

    @Property
    private int outBoundIndex;
    @Property
    private int inBoundIndex;

    public Route getOutboundRoute(){
        outboundRoute = flight.getOutboundRoutes().get(0);
        return outboundRoute;
    }

    public Route getInboundRoute(){
        inboundRoute = flight.getInboundRoutes().get(0);
        return inboundRoute;
    }

    @Persist
    private Route outboundRoute;
    @Persist
    private Route inboundRoute;


    @Property
    private String outSegmentInfo;
    @Property
    private String inSegmentInfo;
    @Property
    private int outRouteSegmentInfoIndex;
    @Property
    private int inRouteSegmentInfoIndex;
    @Property
    private Segment outSegment;
    @Property
    private Segment inSegment;

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
        this.flightSearch = search;
        this.flight = flight;

        passengerList = new ArrayList<Passenger>();
        paymentOptionList = new ArrayList<PaymentOption>();

        for(int index = 0; index < search.getNumberAdults();index++){
            passengerList.add(new Passenger(Passenger.TYPE_ADULT));
        }
        for(int index = 0; index < search.getNumberChildren();index++){
            passengerList.add(new Passenger(Passenger.TYPE_CHILD));
        }
        for(int index = 0; index < search.getNewBorns();index++){
            passengerList.add(new Passenger(Passenger.TYPE_INFANT));
        }

        getOutboundRoute();
        getInboundRoute();

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

    public String getFlightOutLabelDesc() {
        return flight.getOutDescription();
    }

    public String getFlightInLabelDesc() {
        return flight.getInDescription();
    }

    public String getOutRouteScheduleInfo() {
        return outboundRoute.getScheduleDescription();
    }

    public String getInRouteScheduleInfo() {
        return inboundRoute.getScheduleDescription();
    }

    public String getOutRouteSegmentNumber() {
        return outboundRoute.getSegmentsNumber();
    }

    public String getInRouteSegmentNumber() {
        return inboundRoute.getSegmentsNumber();
    }

    public String[] getOutRouteSegmentInfo() {
        return outboundRoute.getSegmentsDescription();
    }

    public String[] getInRouteSegmentInfo() {
        return inboundRoute.getSegmentsDescription();
    }

    public boolean getInContinueRenderingArrow() {
        if (inRouteSegmentInfoIndex < inboundRoute.getSegments().size()) {
            return true;
        }
        return false;
    }

    public boolean getOutContinueRenderingArrow() {
        if (outRouteSegmentInfoIndex < outboundRoute.getSegments().size()) {
            return true;
        }
        return false;
    }

    public Segment[] getOutSegments() {
        return outboundRoute.getSegments().toArray(new Segment[0]);
    }

    public Segment[] getInSegments() {
        return inboundRoute.getSegments().toArray(new Segment[0]);
    }

    @Property
    private final ValueEncoder<Segment> outSegmentsValueEncoder = new ValueEncoder<Segment>() {

        public String toClient(Segment answer) {
            int in = outboundRoute.getSegments().indexOf(answer);
            return String.valueOf(in);
        }

        public Segment toValue(String str) {
            return null;
        }
    };

    @Property
    private final ValueEncoder<Segment> inSegmentsValueEncoder = new ValueEncoder<Segment>() {
        public String toClient(Segment answer) {
            int in = inboundRoute.getSegments().indexOf(answer);
            return String.valueOf(in);
        }

        public Segment toValue(String str) {
            return null;
        }
    };

    public String getOutSegmentWindowDescription() {
        return segmentWindowDescription(outSegment, outSegmentWindowIndex);
    }

    public String getInSegmentWindowDescription() {
        return segmentWindowDescription(inSegment, inSegmentWindowIndex);
    }

    public String segmentWindowDescription(Segment segment, int index) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(NumberHelper.ordinal(index + 1));
        buffer.append(" segment - Flight ");
        buffer.append(segment.getFlightNumber());
        buffer.append(" - ");
        buffer.append(WordUtils.capitalize(segment.getMarketingCabinTypeCode().toLowerCase()));
        buffer.append(" - ");
        buffer.append(segment.getOperatingCarrierDescription());
        return buffer.toString();
    }

    public String getOutSegmentLeaveInfo() {
        return outSegment.getDepartureDescription();
    }

    public String getInSegmentLeaveInfo() {
        return inSegment.getDepartureDescription();
    }

    public String getOutSegmentLeaveHourInfo() {
        return outSegment.getDepartureHourDescription();
    }

    public String getInSegmentLeaveHourInfo() {
        return inSegment.getDepartureHourDescription();
    }

    public boolean getOutSegmentHasStops() {
        return outSegment.getStopovers() != null && outSegment.getStopovers().size() > 0;
    }

    public boolean getInSegmentHasStops() {
        return inSegment.getStopovers() != null && inSegment.getStopovers().size() > 0;
    }

    public String getOutSegmentStopInfo() {
        return outSegment.getStopDescription();
    }

    public String getInSegmentStopInfo() {
        return inSegment.getStopDescription();
    }

    public String getOutSegmentArriveInfo() {
        return outSegment.getArrivalDescription();
    }

    public String getInSegmentArriveInfo() {
        return inSegment.getArrivalDescription();
    }

    public String getOutSegmentArriveHourInfo() {
        return outSegment.getArrivalHourDescription();
    }

    public String getInSegmentArriveHourInfo() {
        return inSegment.getArrivalHourDescription();
    }

    public String getOutSegmentDurationInfo() {
        return outSegment.getDurationDescription();
    }

    public String getInSegmentDurationInfo() {
        return inSegment.getDurationDescription();
    }

    public boolean getIsThereNextOutSegment() {
        return (outSegmentWindowIndex < outboundRoute.getSegments().size() - 1);
    }

    public boolean getIsThereNextInSegment() {
        return (inSegmentWindowIndex < inboundRoute.getSegments().size() - 1);
    }

    public String getInSegmentWaitDesc() {
        return String.format("There will be a wait of  %s", inboundRoute.getWaitDescription(inSegmentWindowIndex, inSegmentWindowIndex + 1));
    }

    public String getOutSegmentWaitDesc() {
        return String.format("There will be a wait of  %s", outboundRoute.getWaitDescription(outSegmentWindowIndex, outSegmentWindowIndex + 1));
    }

    public String getOutRouteDurationInfo() {
        return outboundRoute.getDurationDescription();
    }

    public String getInRouteDurationInfo() {
        return inboundRoute.getDurationDescription();
    }

    public String getOutRouteSegmentInfoCommas() {
        return routeSegmentInfoCommas(outboundRoute);
    }

    public String getInRouteSegmentInfoCommas() {
        return routeSegmentInfoCommas(inboundRoute);
    }

    private String routeSegmentInfoCommas(Route route) {
        StringBuffer buffer = new StringBuffer();

        String[] segmentsDescription = route.getSegmentsDescription();
        int length = segmentsDescription.length;
        for (int index = 0; index < length; index++) {
            buffer.append(segmentsDescription[index]);
            if (index < length - 1) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

    public boolean getIsFlightRound() {
        return flightSearch.isRoundTrip();
    }

    @Property
    private final ValueEncoder<Route> outboundRoutesValueEncoder = new ValueEncoder<Route>() {
        public String toClient(Route answer) {
            int in = flight.getOutboundRoutes().indexOf(answer);
            return String.valueOf(in);
        }

        public Route toValue(String str) {
            return null;
        }
    };

    @Property
    private final ValueEncoder<Route> inboundRoutesValueEncoder = new ValueEncoder<Route>() {

        public String toClient(Route answer) {
            int in = flight.getInboundRoutes().indexOf(answer);
            return String.valueOf(in);
        }

        public Route toValue(String str) {
            return null;
        }
    };
}