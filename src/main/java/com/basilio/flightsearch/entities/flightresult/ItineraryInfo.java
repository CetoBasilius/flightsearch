package com.basilio.flightsearch.entities.flightresult;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 11:52 AM
 * Contains itinerary information
 */

public class ItineraryInfo {
    private boolean canUseAutomaticBooking;
    private String commercialPolicyDescription;
    private Number id;
    private Number inboundRouteIndex;
    private Number outboundRouteIndex;
    private boolean repriceNeeded;
    private Number sequenceNumber;
    private String validatingCarrier;
    private String validatingCarrierDescription;

    public boolean getCanUseAutomaticBooking() {
        return this.canUseAutomaticBooking;
    }

    public void setCanUseAutomaticBooking(boolean canUseAutomaticBooking) {
        this.canUseAutomaticBooking = canUseAutomaticBooking;
    }

    public String getCommercialPolicyDescription() {
        return this.commercialPolicyDescription;
    }

    public void setCommercialPolicyDescription(String commercialPolicyDescription) {
        this.commercialPolicyDescription = commercialPolicyDescription;
    }

    public Number getId() {
        return this.id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getInboundRouteIndex() {
        return this.inboundRouteIndex;
    }

    public void setInboundRouteIndex(Number inboundRouteIndex) {
        this.inboundRouteIndex = inboundRouteIndex;
    }

    public Number getOutboundRouteIndex() {
        return this.outboundRouteIndex;
    }

    public void setOutboundRouteIndex(Number outboundRouteIndex) {
        this.outboundRouteIndex = outboundRouteIndex;
    }

    public boolean getRepriceNeeded() {
        return this.repriceNeeded;
    }

    public void setRepriceNeeded(boolean repriceNeeded) {
        this.repriceNeeded = repriceNeeded;
    }

    public Number getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setSequenceNumber(Number sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getValidatingCarrier() {
        return this.validatingCarrier;
    }

    public void setValidatingCarrier(String validatingCarrier) {
        this.validatingCarrier = validatingCarrier;
    }

    public String getValidatingCarrierDescription() {
        return this.validatingCarrierDescription;
    }

    public void setValidatingCarrierDescription(String validatingCarrierDescription) {
        this.validatingCarrierDescription = validatingCarrierDescription;
    }
}
