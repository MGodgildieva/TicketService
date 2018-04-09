package telran.tickets.entities.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import telran.tickets.api.dto.AddEvent;
import telran.tickets.api.dto.HallEventSeat;
import telran.tickets.entities.users.Organiser;
@Entity
public class Event {
	@Id
	@GeneratedValue
	Integer eventId;
	@ManyToOne(cascade = CascadeType.ALL)
	Organiser org;
	String artist;
	String title;
	String city;
	@Temporal(TemporalType.DATE)
	Date date;
	String time;
	String type;
	String description;
	String imageUrl;
	@ManyToOne(cascade = CascadeType.ALL)
	Hall hall;
	Boolean isHidden;
	Boolean isDeleted;
	@OneToMany (cascade=CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
	List<EventSeat> seats;
	Integer boughtTickets;
	Integer allTickets;
	String priceRange;
	public Event(Organiser org, String artist, String title, String city, Date date, String time, String type,
			String description, String imageUrl, Hall hall, Boolean isHidden, Boolean isDeleted,
			 Integer allTickets, Integer boughtTickets, String priceRange) {
		this.org = org;
		this.artist = artist;
		this.title = title;
		this.city = city;
		this.date = date;
		this.time = time;
		this.type = type;
		this.description = description;
		this.imageUrl = imageUrl;
		this.hall = hall;
		this.isHidden = isHidden;
		this.isDeleted = isDeleted;
		this.seats = new ArrayList<>();
		this.boughtTickets = boughtTickets;
		this.allTickets =  allTickets;
		this.priceRange =  priceRange;
	}
	public Event(AddEvent event, Hall hall, Organiser org) throws Exception {
		this.org = org;
		this.artist = event.getArtist();
		this.title = event.getTitle();
		this.city = event.getCity();
		this.date = new SimpleDateFormat("dd/MM/yyyy").parse(event.getDate());
		this.time = event.getTime();
		this.type = event.getType();
		this.description = event.getDescription();
		this.imageUrl = event.getImageUrl();
		this.hall = hall;
		this.isHidden = false;
		this.isDeleted = false;
		this.seats = new ArrayList<>();
		this.boughtTickets = 0;
		this.allTickets =  event.getAllTickets();
		Set<Integer> prices = new HashSet<>();
		for (HallEventSeat eventSeat : event.getScheme().getSeats()) {
			prices.add(Integer.parseInt(eventSeat.getPrice()));
			seats.add(new EventSeat(this, hall, eventSeat));
		}
		List<Integer> pricesToSort = new ArrayList<>(prices);
		Collections.sort(pricesToSort);
		this.priceRange = pricesToSort.get(0).toString() + "$ - " + pricesToSort.get(prices.size()-1).toString() + "$";
	}
	public Event() {
	}
	public Organiser getOrg() {
		return org;
	}
	public void setOrg(Organiser org) {
		this.org = org;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Hall getHall() {
		return hall;
	}
	public void setHall(Hall hall) {
		this.hall = hall;
	}
	public Boolean getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public List<EventSeat> getSeats() {
		return seats;
	}
	public void setSeats(List<EventSeat> seats) {
		this.seats = seats;
	}
	public void setAllTickets(Integer allTickets) {
		this.allTickets = allTickets;
	}
	public Integer getBoughtTickets() {
		return boughtTickets;
	}
	public void setBoughtTickets(Integer boughtTickets) {
		this.boughtTickets = boughtTickets;
	}
	public Integer getEventId() {
		return eventId;
	}
	
	public Integer getAllTickets() {
		return allTickets;
	}
	
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Event)) {
			return false;
		}
		Event other = (Event) obj;
		if (eventId == null) {
			if (other.eventId != null) {
				return false;
			}
		} else if (!eventId.equals(other.eventId)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "Event [eventId=" + eventId + "]";
	} 
	
	
	
}
