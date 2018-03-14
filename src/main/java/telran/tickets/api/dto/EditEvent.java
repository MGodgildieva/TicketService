package telran.tickets.api.dto;

import java.text.SimpleDateFormat;

import telran.tickets.entities.objects.Event;

public class EditEvent {
	private String email;
	private String eventId;
	private String artist;
	private String title;
	private String city;
	private String date;
	private String time;
	private String description;
	private String type;
	private String imageUrl;
	public EditEvent(String email, String eventId, String artist, String title, String city, String date, String time,
			String description, String type, String imageUrl) {
		this.email = email;
		this.eventId = eventId;
		this.artist = artist;
		this.title = title;
		this.city = city;
		this.date = date;
		this.time = time;
		this.description = description;
		this.type = type;
		this.imageUrl = imageUrl;
	}
	public EditEvent(Event event, String email) {
		this.email = email;
		this.eventId = event.getEventId().toString();
		this.artist = event.getArtist();
		this.title = event.getTitle();
		this.city = event.getCity();
		this.date = new SimpleDateFormat("dd/MM/yyyy").format(event.getDate());
		this.time = event.getTime();
		this.description = event.getDescription();
		this.type = event.getType();
		this.imageUrl = event.getImageUrl();
	}
	public EditEvent() {
	}
	public String getEmail() {
		return email;
	}
	public String getEventId() {
		return eventId;
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
	public String getDescription() {
		return description;
	}
	public String getType() {
		return type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	
	

}
