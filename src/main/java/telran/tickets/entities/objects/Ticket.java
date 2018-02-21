package telran.tickets.entities.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import telran.tickets.api.dto.TicketRequest;

@Entity
public class Ticket {
	@Id
	@GeneratedValue()
	Integer ticketId;
	String eventId;
	String hallId;
	String row;
	String place;
	String price;
	Boolean isBought;
	public Ticket(String eventId, String hallId, String row, String place, String price,
			Boolean isBought) {
		this.eventId = eventId;
		this.hallId = hallId;
		this.row = row;
		this.place = place;
		this.price = price;
		this.isBought = isBought;
	}
	public Ticket(TicketRequest request) {
		this.eventId = request.getEventId();
		this.hallId = request.getHallId();
		this.row = request.getRow();
		this.place = request.getPlace();
		this.price = request.getTicketPrice();
		this.isBought = true;
	}
	public Ticket() {
	}
	public Boolean getIsBought() {
		return isBought;
	}
	public void setIsBought(Boolean isBought) {
		this.isBought = isBought;
	}
	public Integer getTicketId() {
		return ticketId;
	}
	public String getEventId() {
		return eventId;
	}
	public String getHallID() {
		return hallId;
	}
	public String getRow() {
		return row;
	}
	public String getPlace() {
		return place;
	}
	public String getPrice() {
		return price;
	}
	
	

}
