package com.javeninsj.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LogicTest {

	@Test
	void changeVat() {
		TicketDraft td = new TicketDraft("Vilnius", 10);
		assertEquals(td.vatRate, 0.21);
		td.setVat(-0.5);
		assertEquals(td.vatRate, 0.21);
		td.setVat(0.5);
		assertEquals(td.vatRate, 0.5);
	}
	
	@Test
	void basicTravelTest() {
		TicketDraft td = new TicketDraft("Vilnius", 1234.56);
		assertEquals(td.getBaseTravelPrice(), 1234.56);
		assertEquals(td.getRoute(), "Vilnius");
	}

	@Test
	void adultPassengerTest() {
		Passenger pas = new Adult(12.34, 3);
		assertEquals(pas.getLuggageCount(), 3);
		assertEquals(pas.getTicketPrice(), 12.34);
	}
	
	@Test
	void childPassengerTest() {
		Passenger pas = new Child(12.34, 3);
		assertEquals(pas.getLuggageCount(), 3);
		assertEquals(pas.getTicketPrice(), 6.17);
	}
	
	@Test
	void luggageTest() {
		TicketDraft td = new TicketDraft("Vilnius", 10);
		Passenger child = new Child(12.34, 2);
		Passenger adult = new Adult(12.34, 2);
		assertEquals(td.getLuggagePrice(child), 7.26);
		assertEquals(td.getLuggagePrice(adult), 7.26);
	}
	
	@Test
	void givenExampleTest() {
		TicketDraft td = new TicketDraft("Vilnius", 10);
		td.addAdult(2);
		td.addChild(1);
		assertEquals(td.getTicketDraftPrice(), 29.04);
		assertEquals(td.getTicketDraft().size(), 6);
	}	
	
	@Test
	void emptyTripPrice() {
		TicketDraft td = new TicketDraft("Vilnius", 10);
		assertEquals(td.getTicketDraftPrice(), 0);
		assertEquals(td.getTicketDraft().get(0), "Total price = 0.00 EUR");
	}
	
	@Test
	void noLuggageTest() {
		TicketDraft td = new TicketDraft("Vilnius", 10);
		td.addAdult(0);
		assertEquals(td.getTicketDraftPrice(), 12.1);
		assertEquals(td.getTicketDraft().size(), 3);
	}
	
	@Test
	void nullTest() {
		TicketDraft td = new TicketDraft(null, -1);
		assertEquals(td.getBaseTravelPrice(), 0);
		assertEquals(td.getRoute(), "a not specified place");
		
		td = new TicketDraft("", 10);
		assertEquals(td.getRoute(), "a not specified place");
		assertEquals(td.getLuggagePrice(null), 0);
		assertEquals(td.getPassengerTravelPrice(null), 0);
	}
}
