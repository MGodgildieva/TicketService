package telran.tickets.api.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.objects.Ticket;

public class ClientTicket {
	Long ticketId;
	String eventName;
	String eventArtist;
	String eventDate;
	Boolean isAlive;
	List<HallEventSeat> seats;
	public ClientTicket() {
	}
	public ClientTicket(Ticket ticket) {
		this.ticketId = ticket.getTicketId();
		Event event = ticket.getEventSeats().get(0).getEvent();
		if (event.getDate().after(new Date())){
			this.isAlive = true;
		}else {
			this.isAlive = false;
		}
		this.eventName = event.getTitle();
		this.eventArtist = event.getArtist();
		this.eventDate = new SimpleDateFormat("dd/MM/yyyy").format(event.getDate()) + " - " + event.getTime();
		List<HallEventSeat> seats =  new ArrayList<>();
		for (EventSeat seat : ticket.getEventSeats()) {
			seats.add(new HallEventSeat(seat));
		}
		this.seats = seats;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public Boolean getIsAlive() {
		return isAlive;
	}
	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}
	public List<HallEventSeat> getSeats() {
		return seats;
	}
	public void setSeats(List<HallEventSeat> seats) {
		this.seats = seats;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventArtist() {
		return eventArtist;
	}
	public void setEventArtist(String eventArtist) {
		this.eventArtist = eventArtist;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	
	

}
