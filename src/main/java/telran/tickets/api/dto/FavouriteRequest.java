package telran.tickets.api.dto;

public class FavouriteRequest {
	private String email;
	private String eventId;
	private boolean favourite;
	public FavouriteRequest(String email, String eventId, boolean favourite) {
		this.email = email;
		this.eventId = eventId;
		this.favourite = favourite;
	}
	public FavouriteRequest() {
	}
	public String getEmail() {
		return email;
	}
	public String getEventId() {
		return eventId;
	}
	public boolean isFavourite() {
		return favourite;
	}
	
	

}