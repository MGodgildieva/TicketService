package telran.tickets.entities.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import telran.tickets.entities.users.Client;

@Entity
public class Ticket {
	@Id
	private Long ticketId;
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "ticket")
	private List<EventSeat> eventSeats;
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookingTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date buyingTime;
	private Boolean paymentStarted;
	@ManyToOne(cascade = CascadeType.ALL)
	private Client booker;
	@ManyToOne(cascade = CascadeType.ALL)
	private Client buyer;
	private String email;
	private Integer price;
	
	public Ticket() {
	}
	
	public Ticket(Long ticketId, List<EventSeat> eventSeats, Date bookingTime, Date buyingTime, Boolean paymentStarted,
			Client booker, Client buyer, String email, Integer price) {
		this.ticketId = ticketId;
		this.eventSeats = eventSeats;
		this.bookingTime = bookingTime;
		this.buyingTime = buyingTime;
		this.paymentStarted = paymentStarted;
		this.booker = booker;
		this.buyer = buyer;
		this.email = email;
		this.price = price;
	}


	public Ticket(Date bookingTime, Client booker, 
			String email) {
		this.ticketId = bookingTime.getTime();
		this.eventSeats = new ArrayList<>();
		this.bookingTime = bookingTime;
		this.paymentStarted = false;
		this.booker = booker;
		this.email = email;
		this.price = 0;
	}
	public Ticket(Date buyingTime, Boolean paymentStarted, Client buyer, 
			String email) {
		this.ticketId = buyingTime.getTime();
		this.eventSeats = new ArrayList<>();
		this.buyingTime = buyingTime;
		this.paymentStarted = paymentStarted;
		this.buyer = buyer;
		this.email = email;
		this.price = 0;
	}

	public List<EventSeat> getEventSeats() {
		return eventSeats;
	}
	public void setEventSeats(List<EventSeat> eventSeats) {
		this.eventSeats = eventSeats;
	}
	public Date getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}
	public Date getBuyingTime() {
		return buyingTime;
	}
	public void setBuyingTime(Date buyingTime) {
		this.buyingTime = buyingTime;
	}
	public Boolean getPaymentStarted() {
		return paymentStarted;
	}
	public void setPaymentStarted(Boolean paymentStarted) {
		this.paymentStarted = paymentStarted;
	}
	
	public Client getBooker() {
		return booker;
	}

	public void setBooker(Client booker) {
		this.booker = booker;
	}

	public Client getBuyer() {
		return buyer;
	}

	public void setBuyer(Client buyer) {
		this.buyer = buyer;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
	
	

}
