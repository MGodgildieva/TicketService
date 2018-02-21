package telran.tickets.entities.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import telran.tickets.api.dto.AddEvent;
@Entity
public class Event {
	@Id
	@GeneratedValue
	Integer eventId;
	String org;
	String artist;
	String title;
	String city;
	String date;
	String time;
	String type;
	String description;
	String imageUrl;
	String hallId;
	Boolean isHidden;
	Boolean isDeleted;
	@ElementCollection
	@CollectionTable(name = "seats_categories")
	@MapKeyColumn(name = "price")
	@Column(name = "seats")
	Map<Integer, boolean[][]> categories; //bought seats - deleted
	@OneToMany
	Set<Ticket> boughtTickets;
	Integer allTickets;
	public Event(String org, String artist, String title, String city, String date, String time, String type,
			String description, String imageUrl, String hallId, Boolean isHidden, Boolean isDeleted,
			Map<Integer, boolean[][]> categories, Set<Ticket> boughtTickets, Integer allTickets) {
		this.org = org;
		this.artist = artist;
		this.title = title;
		this.city = city;
		this.date = date;
		this.time = time;
		this.type = type;
		this.description = description;
		this.imageUrl = imageUrl;
		this.hallId = hallId;
		this.isHidden = isHidden;
		this.isDeleted = isDeleted;
		this.categories = new HashMap<>();
		this.boughtTickets = new HashSet<>();
		this.allTickets =  allTickets;
	}
	public Event(AddEvent event) {
		this.org = event.getOrg();
		this.artist = event.getArtist();
		this.title = event.getTitle();
		this.city = event.getCity();
		this.date = event.getDate();
		this.time = event.getTime();
		this.type = event.getType();
		this.description = event.getDescription();
		this.imageUrl = event.getImageUrl();
		this.hallId = event.getHallId();
		this.isHidden = false;
		this.isDeleted = false;
		this.categories = new HashMap<>();
		this.boughtTickets = new HashSet<>();
		this.allTickets =  event.getAllTickets();
	}
	public Event() {
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
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
	public String getHallId() {
		return hallId;
	}
	public void setHallId(String hallId) {
		this.hallId = hallId;
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
	public Map<Integer, boolean[][]> getCategories() {
		return categories;
	}
	public void setCategories(Map<Integer, boolean[][]> categories) {
		this.categories = categories;
	}
	public Set<Ticket> getBoughtTickets() {
		return boughtTickets;
	}
	public void setBoughtTickets(Set<Ticket> boughtTickets) {
		this.boughtTickets = boughtTickets;
	}
	public Integer getEventId() {
		return eventId;
	}
	
	public Integer getAllTickets() {
		return allTickets;
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
	
	
}
