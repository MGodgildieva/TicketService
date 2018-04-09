package telran.tickets.entities.objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import telran.tickets.api.dto.HallRequest;
import telran.tickets.api.dto.HallSeat;
import telran.tickets.entities.users.Organiser;
@Entity
public class Hall {
	@Id
	@GeneratedValue()
	Integer hallId;
	@ManyToOne(cascade = CascadeType.ALL)
	Organiser org;
	String hallName;
	String city;
	String street;
	String house;
	String description;
	String width;
	String height;
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "hall", orphanRemoval = true)
	List<Seat> seats;
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "hall", orphanRemoval = true)
	List<Event> events;
	public Hall(String hallName, String city, String street, String house, String description,
			List<Seat> seats, String width, String height, List<Event> events, Organiser org) {
		this.hallName = hallName;
		this.city = city;
		this.street = street;
		this.house = house;
		this.description = description;
		this.seats = seats;
		this.width = width;
		this.height = height;
		this.events = events;
		this.org = org;
	}
	public Hall(String hallName, String city, String street, String house, String description,
			String width, String height) {
		this.hallName = hallName;
		this.city = city;
		this.street = street;
		this.house = house;
		this.description = description;
		this.seats = new ArrayList<>();
		this.width = width;
		this.height = height;
		this.events = new ArrayList<>();
	}
	public Hall(HallRequest request, Organiser org) {
		this.hallName = request.getHallName();
		this.city = request.getCity();
		this.street = request.getStreet();
		this.house = request.getHouse();
		this.description = request.getDescription();
		this.width = request.getWidth();
		this.height = request.getHeight();
		this.seats = new ArrayList<>();
		for (HallSeat hallSeat : request.getHallSeats()) {
			seats.add(new Seat(hallSeat, this));
		}
		this.events = new ArrayList<>();
		this.org = org;
	}
	public Hall() {
	}
	public String getHallName() {
		return hallName;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	public Integer getHallId() {
		return hallId;
	}
	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hallId == null) ? 0 : hallId.hashCode());
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
		if (!(obj instanceof Hall)) {
			return false;
		}
		Hall other = (Hall) obj;
		if (hallId == null) {
			if (other.hallId != null) {
				return false;
			}
		} else if (!hallId.equals(other.hallId)) {
			return false;
		}
		return true;
	}
	
	
}
