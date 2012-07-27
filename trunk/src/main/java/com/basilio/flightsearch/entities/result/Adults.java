
package com.basilio.flightsearch.entities.result;


/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 1:11 PM
 * Results adult field. specifies how many adults and the base fare.
 */

public class Adults{
   	private Number baseFare;
   	private Number quantity;

 	public Number getBaseFare(){
		return this.baseFare;
	}
	public void setBaseFare(Number baseFare){
		this.baseFare = baseFare;
	}
 	public Number getQuantity(){
		return this.quantity;
	}
	public void setQuantity(Number quantity){
		this.quantity = quantity;
	}
}
