package telran.tickets.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.objects.Ticket;

public class ClientTicket {
	Long ticketId;
	Boolean isAlive;
	List<HallEventSeat> seats;
	public ClientTicket() {
	}
	public ClientTicket(Ticket ticket) {
		this.ticketId = ticket.getTicketId();
		if (ticket.getEventSeats().get(0).getEvent().getDate().after(new Date())){
			this.isAlive = true;
		}else {
			this.isAlive = false;
		}
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
	
	

}
