package telran.tickets.api.dto;

import java.text.SimpleDateFormat;

import telran.tickets.entities.objects.Event;

public class FullEventInfo {
	private String phone;
	private String eventId;
	private String artist;
	private String title;
	private String city;
	private String date;
	private String time;
	private String ticketCount;
	private String description;
	private String priceRange;
	private String imageUrl;
	private boolean favourite;
	public FullEventInfo(String phone, String eventId, String artist, String title, String city, String date, String time, String ticketCount,
			String description, String priceRange, String imageUrl, boolean favourite) {
		this.phone = phone;
		this.eventId = eventId;
		this.artist = artist;
		this.title = title;
		this.city = city;
		this.date = date;
		this.time = time;
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
		this.date = new SimpleDateFormat("dd/MM/yyyy").format(event.getDate());
		this.time = event.getTime();
		//this.ticketCount = ((Integer)event.getBoughtTickets().size()).toString();
		this.description = event.getDescription();
		//this.priceRange = priceRange;
		this.imageUrl = event.getImageUrl();
		//this.favourite = favourite;
	}
	public FullEventInfo() {
	}
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
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
	
	

}
