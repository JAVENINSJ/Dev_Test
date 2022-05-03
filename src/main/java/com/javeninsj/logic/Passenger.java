package com.javeninsj.logic;

abstract class Passenger {

	private int luggageCount;
	private double ticketPrice;
	
	Passenger(double ticketPrice, int luggageCount) {
		this.ticketPrice = ticketPrice;
		this.luggageCount = luggageCount;
	}
	
	int getLuggageCount() {
		return luggageCount;
	}

	double getTicketPrice() {
		return ticketPrice;
	}
}
