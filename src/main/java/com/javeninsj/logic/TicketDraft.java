package com.javeninsj.logic;

import java.util.LinkedList;
import java.util.List;

public class TicketDraft {
		
	public double vatRate = 0.21;
	public static final double LUGGAGE_PRICE_RATE = 0.3;
	
	private String routeTo;
	private double baseTravelPrice;
	private List<Passenger> passengers;
	
	/**
	 * Return {@link TicketDraft} object that is used to calculate the travel cost 
	 * @param String of the route end target
	 * @param Base travel price of the travel. Will be set to 0 if input is negative
	 */
	public TicketDraft(String routeTo, double baseTicketPrice) {	
		if(routeTo == null || routeTo == "") routeTo = "a not specified place";
		if(baseTicketPrice < 0) baseTicketPrice = 0;
		
		passengers = new LinkedList<Passenger>();
		this.routeTo = routeTo;
		this.baseTravelPrice = baseTicketPrice;
	}

	/**
	 * Change the value of {@link TicketDraft} VAT. Default 21%
	 * @param The new VAT value, that must be > 0
	 */
	public void setVat(double newVatValue) {
		if(newVatValue < 0) return;
		vatRate = newVatValue;
	}
		
	/**
	 * Add a adult traveler to the ticket draft
	 * @param Luggage count
	 */
	public void addAdult(int luggageCount) {
		if(luggageCount < 0) return;
		passengers.add(new Adult(this.baseTravelPrice, luggageCount));
	}
	
	/**
	 * Add a child traveler to the ticket draft
	 * @param Luggage count
	 */
	public void addChild(int luggageCount) {
		if(luggageCount < 0) return;
		passengers.add(new Child(this.baseTravelPrice, luggageCount));
	}

	/**
	 * Return the travel price of a passenger
	 * @param Passenger that's traveling
	 * @return The travel price
	 */
	public double getPassengerTravelPrice(Passenger passenger) {
		if(passenger == null) return 0;
		return (1 + vatRate) * passenger.getTicketPrice();	
	}
	
	/**
	 * Return the base travel price of the {@link TicketDraft} object
	 * @return The base travel price
	 */
	public double getBaseTravelPrice() {
		return baseTravelPrice;	
	}	
	
	/**
	 * Return the luggage price of a passenger
	 * @param Passenger that's traveling
	 * @return The luggage price
	 */
	public double getLuggagePrice(Passenger passenger) {
		if(passenger == null) return 0;
		return (1 + vatRate) * passenger.getLuggageCount() * LUGGAGE_PRICE_RATE * baseTravelPrice;	
	}	
	
	/**
	 * Return the full ticket draft price
	 * @return The ticket draft price
	 */
	public double getTicketDraftPrice(){
		
		if (passengers.size() == 0) return 0;
		
		double totalTicketPrice = 0;
		
		for(Passenger passenger: passengers) {
			totalTicketPrice += getPassengerTravelPrice(passenger);	
			if(passenger.getLuggageCount() == 0) continue;
			totalTicketPrice += getLuggagePrice(passenger);
		}
		
		return totalTicketPrice;
	}

	/**
	 * Return the full ticket draft with each entry as a list of Strings
	 * @return The full ticket draft as a list of Strings
	 */
	public List<String> getTicketDraft(){
		
		List<String> output = new LinkedList<String>();
		if (passengers.size() == 0) {
			output.add("Total price = 0.00 EUR");
			return output;
		}
		
		output.add("Ticket for route to " + routeTo);
		
		double linePrice, totalTicketPrice = 0;
		
		for(Passenger passenger: passengers) {
			
			linePrice = getPassengerTravelPrice(passenger);
			
			output.add(String.format("%s (%.2f EUR) + %.2f%% = %.2f EUR\n",
					passenger.getClass().getSimpleName(),
					passenger.getTicketPrice(),
					vatRate * 100,
					linePrice
			));
			
			totalTicketPrice += linePrice;
			
			if(passenger.getLuggageCount() == 0) continue;
			
			linePrice = getLuggagePrice(passenger); 
			
			output.add(String.format(" - %d bag(s) (%.2f EUR x %.2f%%) + %.2f%% = %.2f EUR\n",
					passenger.getLuggageCount(),
					linePrice,
					LUGGAGE_PRICE_RATE * 100,
					vatRate * 100,
					linePrice
			));
			
			totalTicketPrice += linePrice;
		}
		
		output.add(String.format("Total Price = %.2f EUR", totalTicketPrice));
		
		return output;
	}
	
	/**
	 * Return the route to where the {@link TicketDraft} trip is heading to.
	 * @return The Ticket Draft object route
	 */
	public String getRoute() {
		return routeTo;
	}
}
