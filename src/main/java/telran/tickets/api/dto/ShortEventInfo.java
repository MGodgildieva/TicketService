package telran.tickets.api.dto;

import telran.tickets.entities.objects.Event;

public class ShortEventInfo {
	private String eventId;
	private String title;
	private String artist;
	private String date;
	private String imageUrl;
	public ShortEventInfo(String eventId, String title, String artist, String date, String imageUrl) {
		this.eventId = eventId;
		this.title = title;
		this.artist = artist;
		this.date = date;
		this.imageUrl = imageUrl;
	}
	public ShortEventInfo(Event event) {
		this.eventId = event.getEventId().toString();
		this.title = event.getTitle();
		this.artist = event.getArtist();
		this.date = event.getDate();
		this.imageUrl = event.getImageUrl();
	}
	
	public ShortEventInfo() {
	}
	public String getEventId() {
		return eventId;
	}
	public String getTitle() {
		return title;
	}
	public String getArtist() {
		return artist;
	}
	public String getDate() {
		return date;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	
	

}
