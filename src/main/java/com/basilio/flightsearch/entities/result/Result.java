
package com.basilio.flightsearch.entities.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:49 PM
 * Base result class
 */

public class Result{
   	private List<Flights> flights;
   	private Meta meta;
    private int searchedPrice;

    public List<Flights> getDirectFlights(){
        List<Flights> returnFlights = new ArrayList<Flights>();
        List<Flights> flights1 = this.getFlights();
        for(int index = 0; index < flights1.size(); index++){
            List<OutboundRoutes> outboundRoutes = flights1.get(index).getOutboundRoutes();
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
        buffer.append("We found ");
        buffer.append(this.getFlights().size());
        buffer.append(" flights matching your search, of which ");
        buffer.append(this.getFlightsInPriceRange().size());
        buffer.append(" matched your budget of no more than ");
        buffer.append(this.getSearchedPrice());
        buffer.append(" dollars. The most economic flight is ");
        buffer.append(this.getMeta().getFacets().get(2).getMin());
        buffer.append(" dollars per person, and the most expensive flight is ");
        buffer.append(this.getMeta().getFacets().get(2).getMax());
        buffer.append(" dollars. ");

        return buffer.toString();
    }

    private List<Flights> getFlightsInPriceRange() {
        List<Flights> returnFlights = new ArrayList<Flights>();
        List<Flights> flights1 = this.getFlights();
        for(int index = 0; index < flights1.size(); index++){
            Total total = flights1.get(index).getPriceInfo().getTotal();
            if((total.getFare().intValue())<this.getSearchedPrice()){
                returnFlights.add(flights1.get(index));
            }
        }
        return returnFlights;
    }


    public List<Flights> getFlights(){
        return this.flights;
    }
    public void setFlights(List<Flights> flights){
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
}
