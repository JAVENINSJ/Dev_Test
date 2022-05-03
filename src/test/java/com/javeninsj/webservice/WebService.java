package com.javeninsj.webservice;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WebService {
	
	@Test
	void indexTest() {
		TicketDraftServiceControler ctrl = new TicketDraftServiceControler();
		
		assertThatNoException().isThrownBy(() -> {
			ctrl.index();
		});
	}
	
	@Test
	void newDraftTest() {
		TicketDraftServiceControler ctrl = new TicketDraftServiceControler();
		
		assertEquals(ctrl.newTicketDraft("Vilnius", 10),
				"<p>Started price draft to Vilnius where ticket price is: 10.00 EUR</p>");
	}

	@Test
	void exampleTicketDraftTest() {
		TicketDraftServiceControler ctrl = new TicketDraftServiceControler();
		
		assertEquals(ctrl.getTicketDraft(),
				"<p>Price draft is not created.</p>" +
			    "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>");
		
		ctrl.newTicketDraft("Vilnius", 10);
		ctrl.addAdult(2);
		ctrl.addChild(1);

		assertEquals(ctrl.getTicketDraft()
							.substring(
								ctrl.getTicketDraft().length() - 13,
								ctrl.getTicketDraft().length() - 4),
					"29.04 EUR");
	}
	

	@Test
	void setNewVat() {
		TicketDraftServiceControler ctrl = new TicketDraftServiceControler();
		
		assertEquals(ctrl.setVat(30),
				"<p>Price draft is not created.</p>" +
			    "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>");
		ctrl.newTicketDraft("Vilnius", 10);
		assertEquals(ctrl.setVat(-10),
				"<p>VAT value is not changed.</p>" +
				"<p>VAT value must be > 0</p>");
		assertEquals(ctrl.setVat(30),
				"<p>VAT value changed to 30.0%<p>");
	}
	
	@Test
	void addingPassengersTest() {
		TicketDraftServiceControler ctrl = new TicketDraftServiceControler();
		
		assertEquals(ctrl.addAdult(1),
				"<p>Price draft is not created.</p>" +
			    "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>");
		assertEquals(ctrl.addChild(1),
				"<p>Price draft is not created.</p>" +
			    "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>");
		ctrl.newTicketDraft("Vilnius", 10);
		assertEquals(ctrl.addAdult(-1),
				"<p>Luggage count can not be negative</p>");
		assertEquals(ctrl.addChild(-1),
				"<p>Luggage count can not be negative</p>");
		
		ctrl.addAdult(2);
		ctrl.addChild(1);

		assertEquals(ctrl.getTicketDraft()
							.substring(
								ctrl.getTicketDraft().length() - 13,
								ctrl.getTicketDraft().length() - 4),
					"29.04 EUR");
	}
	
}
