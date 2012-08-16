package com.basilio.flightsearch.entities.flightresult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 11:22 AM
 * Actual flight result.
 */
public class Flight {

    public final static int ANY_SEGMENTS = 1;
    public final static int ONE_SEGMENT = 2;
    public final static int TWO_OR_MORE_SEGMENTS = 3;

    private String id;
    private List<Route> inboundRoutes;
    private List<ItineraryInfo> itineraryInfos;
    private List<Route> outboundRoutes;
    private PaymentInfo paymentInfo;
    private PriceInfo priceInfo;

    public Flight() {
        inboundRoutes = new ArrayList<Route>();
        itineraryInfos = new ArrayList<ItineraryInfo>();
        outboundRoutes = new ArrayList<Route>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Route> getInboundRoutes() {
        return this.inboundRoutes;
    }

    public void setInboundRoutes(List<Route> inboundRoutes) {
        this.inboundRoutes = inboundRoutes;
    }

    public List<ItineraryInfo> getItineraryInfos() {
        return this.itineraryInfos;
    }

    public void setItineraryInfos(List<ItineraryInfo> itineraryInfos) {
        this.itineraryInfos = itineraryInfos;
    }

    public List<Route> getOutboundRoutes() {
        return this.outboundRoutes;
    }

    public void setOutboundRoutes(List<Route> outboundRoutes) {
        this.outboundRoutes = outboundRoutes;
    }

    public PaymentInfo getPaymentInfo() {
        return this.paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public PriceInfo getPriceInfo() {
        return this.priceInfo;
    }

    public void setPriceInfo(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getOutDescription() {
        StringBuffer buffer = new StringBuffer();
        if (outboundRoutes.size() > 0) {
            buffer.append(outboundRoutes.get(0).getDescription());
        } else {
            buffer.append("Outbound routes was empty");
        }
        return buffer.toString();
    }

    public String getInDescription() {
        StringBuffer buffer = new StringBuffer();
        if (inboundRoutes.size() > 0) {
            buffer.append(inboundRoutes.get(0).getDescription());
        } else {
            buffer.append("Inbound routes was empty");
        }
        return buffer.toString();
    }

    public boolean outboundHasSegments(int segmentNumber) {
        for (int index = 0; index < outboundRoutes.size(); index++) {
            int size = outboundRoutes.get(index).getSegments().size();
            switch (segmentNumber) {
                case ANY_SEGMENTS: {
                    return true;
                }
                case TWO_OR_MORE_SEGMENTS: {
                    return size > 1;

                }

                case ONE_SEGMENT: {
                    if (size == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param segmentNumber
     * @return will return true if inbound route has the specified segment rule or if inbound is empty or null
     */
    public boolean inboundHasSegments(int segmentNumber) {
        for (int index = 0; index < inboundRoutes.size(); index++) {
            int size = inboundRoutes.get(index).getSegments().size();
            switch (segmentNumber) {
                case ANY_SEGMENTS: {
                    return true;
                }
                case TWO_OR_MORE_SEGMENTS: {
                    if (size > 1) {
                        return true;
                    } else {
                        return false;
                    }
                }

                case ONE_SEGMENT: {
                    if (size == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
