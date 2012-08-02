
package com.basilio.flightsearch.entities.flightresult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:49 PM
 * Base result class
 */

public class FlightResult implements Cloneable{
   	private List<Flight> flights;
   	private Meta meta;

    private int searchedPrice;
    private boolean searchedDirect;

    public List<Flight> getDirectFlights(){
        List<Flight> returnFlights = new ArrayList<Flight>();
        List<Flight> flights1 = this.getFlights();
        for(int index = 0; index < flights1.size(); index++){
            List<Route> outboundRoutes = flights1.get(index).getOutboundRoutes();
            for(int routeIndex = 0; routeIndex < outboundRoutes.size(); routeIndex++){
                if(outboundRoutes.get(routeIndex).getSegments().size()==1){
                    returnFlights.add(flights1.get(index));
                    break;
                }
            }
        }
        return returnFlights;
    }

    public String getDescription() {
        StringBuffer buffer = new StringBuffer();
        if(flights!=null){
            if(flights.size()>0){
                buffer.append("We found ");
                buffer.append(this.getFlights().size());
                buffer.append(" flights matching your search, of which ");
                buffer.append(this.getFlightsInPriceRange().size());
                buffer.append(" matched your budget of no more than ");
                buffer.append(this.getSearchedPrice());
                buffer.append(" dollars");
                if(this.isSearchedDirect()){
                    buffer.append(" and are direct flights");
                }
                buffer.append(". The most economic flight is ");
                buffer.append(this.getMeta().getFacets().get(2).getMin());
                buffer.append(" dollars per adult.");/*, and the most expensive flight is ");
                buffer.append(this.getMeta().getFacets().get(2).getMax());
                buffer.append(" dollars per adult. ");*/
            } else {
                buffer.append("We are sorry, but your search had no results.");
            }
        }
        return buffer.toString();
    }

    public List<Flight> getFlightsInPriceRange() {
        List<Flight> returnFlights = new ArrayList<Flight>();
        List<Flight> flights1;
        if(this.isSearchedDirect()){
            flights1 = this.getDirectFlights();
        } else{
            flights1 = this.getFlights();
        }

        for(int index = 0; index < flights1.size(); index++){
            Total total = flights1.get(index).getPriceInfo().getTotal();
            Number fare = total.getFare();
            if((fare.intValue())<this.getSearchedPrice()){
                returnFlights.add(flights1.get(index));
            }
        }
        return returnFlights;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public List<Flight> getFlights(){
        return this.flights;
    }
    public void setFlights(List<Flight> flights){
        this.flights = flights;
    }
    public Meta getMeta(){
        return this.meta;
    }
    public void setMeta(Meta meta){
        this.meta = meta;
    }

    public int getSearchedPrice() {
        return searchedPrice;
    }

    public void setSearchedPrice(int searchedPrice) {
        this.searchedPrice = searchedPrice;
    }

    public boolean isSearchedDirect() {
        return searchedDirect;
    }

    public void setSearchedDirect(boolean searchedDirect) {
        this.searchedDirect = searchedDirect;
    }
}
