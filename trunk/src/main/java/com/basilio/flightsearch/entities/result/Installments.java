
package com.basilio.flightsearch.entities.result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */

public class Installments{
   	private Number first;
   	private Number others;
   	private Number quantity;

 	public Number getFirst(){
		return this.first;
	}
	public void setFirst(Number first){
		this.first = first;
	}
 	public Number getOthers(){
		return this.others;
	}
	public void setOthers(Number others){
		this.others = others;
	}
 	public Number getQuantity(){
		return this.quantity;
	}
	public void setQuantity(Number quantity){
		this.quantity = quantity;
	}
}
