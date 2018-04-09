package telran.tickets.api.dto;

import java.text.SimpleDateFormat;
import java.util.Locale;

import telran.tickets.entities.objects.Event;

public class FullEventInfo {
	private String email;
	private String eventId;
	private String artist;
	private String title;
	private String city;
	private String date;
	private String time;
	private String type;
	private String ticketCount;
	private String description;
	private String priceRange;
	private String imageUrl;
	private boolean favourite;
	public FullEventInfo(String email, String eventId, String artist, String title, String city, String date, String time, String type, String ticketCount,
			String description, String priceRange, String imageUrl, boolean favourite) {
		this.email = email;
		this.eventId = eventId;
		this.artist = artist;
		this.title = title;
		this.city = city;
		this.date = date;
		this.time = time;
		this.type = type;
		this.ticketCount = ticketCount;
		this.description = description;
		this.priceRange = priceRange;
		this.imageUrl = imageUrl;
		this.favourite = favourite;
	}
	public FullEventInfo(Event event) {
		this.eventId = event.getEventId().toString();
		this.artist = event.getArtist();
		this.title = event.getTitle();
		this.city = event.getCity();
		this.date = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(event.getDate());
		this.time = event.getTime();
		this.type = event.getType();
		this.description = event.getDescription();
		this.imageUrl = event.getImageUrl();
	}
	public FullEventInfo() {
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEventId() {
		return eventId;
	}
	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
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
	public String getTicketCount() {
		return ticketCount;
	}
	public String getDescription() {
		return description;
	}
	public String getPriceRange() {
		return priceRange;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public boolean isFavourite() {
		return favourite;
	}
	public String getType() {
		return type;
	}
}
