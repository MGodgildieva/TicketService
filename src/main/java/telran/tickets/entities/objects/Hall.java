package telran.tickets.entities.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Hall {
	@Id
	@GeneratedValue()
	Integer hallId;
	String hallName;
	String city;
	String street;
	String house;
	String description;
	boolean [][] seats; // есть место, нет места
	public Hall(String hallName, String city, String street, String house, String description,
			boolean[][] seats) {
		this.hallName = hallName;
		this.city = city;
		this.street = street;
		this.house = house;
		this.description = description;
		this.seats = seats;
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
	public boolean[][] getSeats() {
		return seats;
	}
	public void setSeats(boolean[][] seats) {
		this.seats = seats;
	}
	public Integer getHallId() {
		return hallId;
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
