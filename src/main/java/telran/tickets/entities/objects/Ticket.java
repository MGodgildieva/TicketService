package telran.tickets.entities.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ticket {
	@Id
	String ticketId;
	String event;
	String hall;
	String row;
	String place;
	String price;
	Boolean isBought;

}
