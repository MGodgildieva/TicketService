package telran.tickets.api.dto;

public class EditEvent {
	private String orgId;
	private String eventId;
	private String artist;
	private String title;
	private String date;
	private String time;
	private String description;
	private String type;
	private String imageUrl;
	public EditEvent(String orgId, String eventId, String artist, String title, String date, String time,
			String description, String type, String imageUrl) {
		this.orgId = orgId;
		this.eventId = eventId;
		this.artist = artist;
		this.title = title;
		this.date = date;
		this.time = time;
		this.description = description;
		this.type = type;
		this.imageUrl = imageUrl;
	}
	public EditEvent() {
	}
	public String getOrgId() {
		return orgId;
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
