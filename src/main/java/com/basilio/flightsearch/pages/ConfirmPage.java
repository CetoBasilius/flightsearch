package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.Window;
import com.basilio.flightsearch.core.helpers.NumberHelper;
import com.basilio.flightsearch.entities.CardDetails;
import com.basilio.flightsearch.entities.ContactInformation;
import com.basilio.flightsearch.entities.PaymentOption;
import com.basilio.flightsearch.entities.flightresult.*;
import com.basilio.flightsearch.entities.Passenger;
import com.basilio.flightsearch.services.BookingService;
import com.basilio.flightsearch.services.BookingServiceImpl;
import org.apache.commons.lang.WordUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import java.util.*;

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
    private List<String> birthDayList;

    @Persist
    @Property
    private List<String> birthYearList;

    @Persist
    @Property
    private String gender;
    @Persist
    @Property
    private boolean agreeTerms;

    @Persist
    @Property
    private ContactInformation contactInformation;

    @Persist
    @Property
    private Passenger passenger;

    @Persist
    @Property
    private int paymentIndex;

    @Persist
    @Property
    private CardDetails cardDetails;

    @Inject
    private BookingService bookingService;

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

    @Component(parameters = {"style=greylighting",
            "show=false",
            "modal=true",
            "width=500",
            "title=literal:Terms and conditions"})
    private Window termsWindow;

    @Property
    private int outBoundIndex;
    @Property
    private int inBoundIndex;

    public ConfirmPage() {
    }

    public Route getOutboundRoute(){
        outboundRoute = flight.getOutboundRoutes().get(0);
        return outboundRoute;
    }

    public Route getInboundRoute(){
        if(flight.getInboundRoutes().size()>0){
            inboundRoute = flight.getInboundRoutes().get(0);
        }
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
    @Property
    @Persist
    private List<String> availableCards;

    @Property
    private List<String> allMonths;
    @Property
    private List<String> nextYears;

    @Property
    private String paymentRadioSelectedValue;

    @Inject
    private ComponentResources componentResources;
    @Property
    private PaymentOption paymentOption;
    @Property
    private String paymentRadio;

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
            return String.valueOf(in);
        }

        public PaymentOption toValue(String str) {
            int index = Integer.parseInt(str);
            return paymentOptionList.get(index);
        }
    };

    @Persist(PersistenceConstants.FLASH)
    private int numAdult;
    @Persist(PersistenceConstants.FLASH)
    private int numChild;
    @Persist(PersistenceConstants.FLASH)
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

    public int getNumAdults(){
        return numAdult;
    }

    public int getNumChildren(){
        return numChild;
    }

    public int getNumInfants(){
        return numInfant;
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

        bookingService.bookFlight(passengerList,contactInformation,flight,cardDetails);

        return null;
    }

    public String getPaymentDescription(){
        return paymentOptionList.get(paymentIndex).getDescription();
    }

    public void setupRender(){
        resetNumberOfPersons();

        createCardDetailsIfNull();
        createAvailableCardsIfNull();

        createAllMonths();
        Calendar cal = Calendar.getInstance();
        createNextYears(cal);
        createBirthDayList();
        createBirthYearList(cal);
        populatePaymentOptionsList(cardDetails.getCardType());
    }

    private void resetNumberOfPersons() {
        numAdult=0;
        numChild=0;
        numInfant=0;
    }

    private void createAvailableCardsIfNull() {
        if(availableCards == null){
            availableCards = new ArrayList<String>();
            availableCards.add("No card");
        }
        if(availableCards.size()>0){
            cardDetails.setCardType(availableCards.get(0));
        }
    }

    private void createCardDetailsIfNull() {
        if(cardDetails == null){
            cardDetails = new CardDetails();
        }
    }

    private void createAllMonths() {
        if(allMonths == null){
            allMonths = new ArrayList<String>();
            allMonths.add("Jan");
            allMonths.add("Feb");
            allMonths.add("Mar");
            allMonths.add("Apr");
            allMonths.add("May");
            allMonths.add("Jun");
            allMonths.add("Jul");
            allMonths.add("Aug");
            allMonths.add("Sep");
            allMonths.add("Oct");
            allMonths.add("Nov");
            allMonths.add("Dec");
        }
    }

    private void createNextYears(Calendar cal) {
        if(nextYears == null){
            nextYears = new ArrayList<String>();
            int nextYear = cal.get(Calendar.YEAR);
            for(int index = 1; index < 20; index++){
                nextYears.add(String.valueOf(nextYear+index));
            }
        }
    }

    private void createBirthDayList() {
        if(birthDayList == null){
            birthDayList = new ArrayList<String>();
            for(int day = 1;day <= 31; day++){
                birthDayList.add(String.valueOf(day));
            }
        }
    }

    private void createBirthYearList(Calendar cal) {
        if(birthYearList == null){
            birthYearList = new ArrayList<String>();
            int thisYear = cal.get(Calendar.YEAR);
            for(int year = 0; year > -110; year--){
                birthYearList.add(String.valueOf(thisYear+year));
            }
        }
    }

    public void setup(FlightSearch search, Flight flight) {
        resetNumberOfPersons();

        cardDetails = new CardDetails();
        contactInformation = new ContactInformation();
        availableCards = new ArrayList<String>();

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
            addCardToList(payments.get(index));
        }
        if(availableCards.size()>0){
            cardDetails.setCardType(availableCards.get(0));
        }

        populatePaymentOptionsList(cardDetails.getCardType());
    }

    private void addCardToList(Payment payment) {
        if(availableCards.size()>0){
            boolean cardNameAlreadyExists = false;
            for(String card : availableCards){
                if(card.equals(payment.getCardDescription())){
                    cardNameAlreadyExists = true;
                    break;
                }
            }
            if(!cardNameAlreadyExists){
                availableCards.add(payment.getCardDescription());
            }
        } else{
            availableCards.add(payment.getCardDescription());
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

    @InjectComponent
    private Zone paymentOptionsZone;

    Object onValueChangedFromCardType(String cardType) {
        populatePaymentOptionsList(cardType);
        return request.isXHR() ? paymentOptionsZone.getBody() : null;
    }

    private void populatePaymentOptionsList(String cardType) {
        paymentOptionList = new ArrayList<PaymentOption>();
        for(int index = 0; index< flight.getPaymentInfo().getPayments().size();index++){
            Payment payment = flight.getPaymentInfo().getPayments().get(index);
            if(payment.getCardDescription().equals(cardType)){
                PaymentOption paymentOption1 = new PaymentOption();
                paymentOption1.setNumberOfPayments((Integer) payment.getInstallments().getQuantity());
                paymentOption1.setFirstPayment((Integer) payment.getInstallments().getFirst());
                paymentOption1.setOtherPayments((Integer) payment.getInstallments().getOthers());
                paymentOption1.getCards().add(payment.getCardDescription());

                paymentOptionList.add(paymentOption1);
            }
        }
    }

    public String getAdultTotalPrice(){
        return String.valueOf((Integer) flight.getPriceInfo().getAdults().getBaseFare() * numAdult);
    }

    public String getChildrenTotalPrice(){
        return numChild>0 ? String.valueOf((Integer)flight.getPriceInfo().getChildren().getBaseFare()*numChild) : "0";
    }

    public String getInfantsTotalPrice(){
        return numInfant>0 ? String.valueOf((Integer)flight.getPriceInfo().getInfants().getBaseFare()*numInfant) : "0";
    }

    public String getTaxes(){
        return String.valueOf((Integer)flight.getPriceInfo().getTotal().getTaxes());
    }

    public String getCharges(){
        return String.valueOf((Integer)flight.getPriceInfo().getTotal().getCharges());
    }

    public String getPriceTotal(){
        return String.valueOf((Integer)flight.getPriceInfo().getTotal().getFare());
    }

}
