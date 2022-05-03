package com.javeninsj.webservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.javeninsj.logic.TicketDraft;

@RestController
public class TicketDraftServiceControler {
	
	private TicketDraft priceDraft;
	
	@GetMapping("/")
	public String index() {
		String output = "<h1>Index page</h1>";
		String[][] linkMap = {
				{"/newDraft/Vilnius/10", "Create a trip to Vilnius for 10€"},
				{"/setVAT/30", "Set VAT to 30%"},
				{"/addAdult/1", "Add an adult with 1 luggage"}, 
				{"/addChild/1", "Add a child with 1 luggage"}, 
				{"/getTicketDraft", "Get ticket price draft"}
		};
		
		output += "<ul>";
		for(String[] link: linkMap) {
			output += String.format("<li><a href=\"%s\" target=\"_blank\" rel=\"noopener noreferrer\">%s</a></li>", link[0], link[1]);
		}
		output += "</ul>";
		
		return output;
	}
	
	@GetMapping("/getTicketDraft")
	public String getTicketDraft() {
		if(priceDraft == null) {
			return "<p>Price draft is not created.</p>" +
				   "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>";
		}
		String output = "";
		for(String str: priceDraft.getTicketDraft()) {
			output += "<p>" + str + "</p>";
		}
		return output;
	}
	
	@GetMapping("/newDraft/{route}/{basePrice}")
	public String newTicketDraft(@PathVariable("route") String toRoute,
								 @PathVariable("basePrice") double basePrice) {
		priceDraft = new TicketDraft(toRoute, basePrice);
		
		System.out.printf("Started price draft to %s where ticket price is: %.2f EUR\n",
				priceDraft.getRoute(),
				priceDraft.getBaseTravelPrice());
		return String.format("<p>Started price draft to %s where ticket price is: %.2f EUR</p>",
				priceDraft.getRoute(),
				priceDraft.getBaseTravelPrice());
	}
	
	@GetMapping("/setVAT/{newVatVal}")
	public String setVat(@PathVariable("newVatVal") double newVatVal) {
		if(priceDraft == null) {
			return "<p>Price draft is not created.</p>" +
				   "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>";
		}
		if(newVatVal < 0) {
			return "<p>VAT value is not changed.</p>" +
				   "<p>VAT value must be > 0</p>";
		}
		priceDraft.setVat(newVatVal / 100);
		return String.format("<p>VAT value changed to %2.1f%%<p>", newVatVal);
	}
	
	@GetMapping("/addChild/{luggageCount}")
	public String addChild(@PathVariable("luggageCount") int luggageCount) {
		if(priceDraft == null) {
			return "<p>Price draft is not created.</p>" +
				   "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>";
		}
		if(luggageCount < 0) {
			return "<p>Luggage count can not be negative</p>";
		}
		priceDraft.addChild(luggageCount);
		return String.format("<p>Child ticket added! With %d luggage.<p>", luggageCount);
	}
	
	@GetMapping("/addAdult/{luggageCount}")
	public String addAdult(@PathVariable("luggageCount") int luggageCount) {
		if(priceDraft == null) {
			return "<p>Price draft is not created.</p>" +
				   "<p>Reach out to /newDraft/{route}/{basePrice} to start a ticket price draft.</p>";
		}
		if(luggageCount < 0) {
			return "<p>Luggage count can not be negative</p>";
		}
		
		priceDraft.addAdult(luggageCount);
		return String.format("<p>Adult ticket added! With %d luggage.<p>", luggageCount);
	}
	
	
}