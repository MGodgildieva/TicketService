package telran.tickets.entities.objects;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import telran.tickets.api.dto.HallEventSeat;

@Entity
public class EventSeat {
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne (cascade = CascadeType.ALL)
	private Event event;
	private String price;
	@ManyToOne (cascade = CascadeType.ALL)
	private Seat seat;
	private boolean isTaken;
	@ManyToOne()
	private Ticket ticket;
	private Integer colour;
	public EventSeat() {
	}
	public EventSeat(Event event, Hall hall, HallEventSeat seatDto) throws Exception {
		this.event =  event;
		this.price = seatDto.getPrice().toString();
		this.colour = seatDto.getColour();
		this.isTaken = seatDto.getIsTaken();
		List<Seat> seats =  hall.getSeats();
		for (Seat seat : seats) {
			if (seat.getPlace().equals(seatDto.getPlace()) && seat.getRow().equals(seatDto.getRow())) {
				this.seat = seat;
			}
		}
		
	}
	public EventSeat(Event event, Hall hall, Seat seat, String price, Integer colour) throws Exception {
		this.event =  event;
		this.price = price;
		this.colour = colour;
		this.isTaken = false;
		this.seat = seat;
		
	}
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public boolean getIsTaken() {
		return isTaken;
	}
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	public Integer getId() {
		return id;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public Integer getColour() {
		return colour;
	}
	public void setColour(Integer colour) {
		this.colour = colour;
	}
	
	
	
	
	
	

}
