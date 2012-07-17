
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
}
