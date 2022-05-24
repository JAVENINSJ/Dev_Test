# Dev Test project

The project opens a webservice on port :8080

## Creating a ticket draft
To create a ticket draft first create it by requesting "/newDraft/{route}/{basePrice}" where:
route - Location to where the trip is going
basePrice - Double value of how much the base ticket costs. If value is < 0, the base price will be set as 0.

## Changing VAT
The default VAT value is 21%. 
To change it request "/setVAT/{newVatVal}" where:
newVatVal - The new VAT value as a percentage. If provided value is < 0, a new value will not be set.

For example requesting "/setVAT/30" will set the VAT value to 30%.

##  Adding passengers
To add a passenger make a request to "/addChild/{luggageCount}" or "/addAdult/{luggageCount}" where:
luggageCount - The amount of luggage the passenger has with them.

The child ticket has a 50% discount.

## Obtaining ticket draft
To receive a ticket draft, make a request to "/getTicketDraft"
The ticket draft will be returned

## Index page
There is an index page with some basic options available located at root.
