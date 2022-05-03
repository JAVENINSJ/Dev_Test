package com.javeninsj.logic;

class Child extends Passenger{

	public static final double CHILD_DISCOUNT = 0.5;

	Child(double ticketPrice, int luggageCount) {
		super(ticketPrice * CHILD_DISCOUNT, luggageCount);
	}	
}
