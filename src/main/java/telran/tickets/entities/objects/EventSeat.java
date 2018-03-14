package telran.tickets.entities.objects;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import telran.tickets.api.dto.HallEventSeat;
import telran.tickets.entities.users.Client;

@Entity
public class EventSeat {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne (cascade = CascadeType.ALL)
	private Event event;
	private String price;
	@ManyToOne (cascade = CascadeType.ALL)
	private Seat seat;
	private boolean isTaken;
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookingTime;
	@OneToOne(cascade = CascadeType.ALL)
	private Client buyer;
	public EventSeat() {
	}
	public EventSeat(Event event, Hall hall, HallEventSeat seatDto) throws Exception {
		this.event =  event;
		this.price = seatDto.getPrice();
		this.isTaken = seatDto.isAvailable();
		this.bookingTime = null;
		this.buyer =  null;
		List<Seat> seats =  hall.getSeats();
		for (Seat seat : seats) {
			if (seat.getPlace().equals(seatDto.getPlace()) && seat.getRow().equals(seatDto.getRow())) {
				this.seat = seat;
				break;
			}else {
				throw new Exception();
			}
		}
		
	}
	
	

	
	
	public Client getBuyer() {
		return buyer;
	}
	public void setBuyer(Client buyer) {
		this.buyer = buyer;
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
	public boolean isTaken() {
		return isTaken;
	}
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	public int getId() {
		return id;
	}
	public Date getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}
	
	
	

}
