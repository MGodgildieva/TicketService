package telran.tickets.api.dto;

public class AddEvent {

	String email;
	String artist;
	String title;
	String city;
	String date;
	String time;
	String type;
	String description;
	String imageUrl;
	String hallId;
	Integer allTickets;
	EventScheme scheme;
	public AddEvent(String org, String artist, String title, String city, String date, String time, String type,
			String description, String imageUrl, String hallId, Integer allTickets, EventScheme scheme) {
		this.email = org;
		this.artist = artist;
		this.title = title;
		this.city = city;
		this.date = date;
		this.time = time;
		this.type = type;
		this.description = description;
		this.imageUrl = imageUrl;
		this.hallId = hallId;
		this.allTickets = allTickets;
		this.scheme = scheme;
	}
	public AddEvent() {
	}
	public String getEmail() {
		return email;
	}
	public String getArtist() {
		return artist;
	}
	public String getTitle() {
		return title;
	}
	public String getCity() {
		return city;
	}
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getHallId() {
		return hallId;
	}
	public Integer getAllTickets() {
		return allTickets;
	}
	public EventScheme getScheme() {
		return scheme;
	}
	
	
	
	
}
