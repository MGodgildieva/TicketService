package telran.tickets.api.dto;

public class FavouriteRequest {
	private String email;
	private String eventId;
	private boolean isFavourite;
	public FavouriteRequest(String email, String eventId, boolean isFavourite) {
		this.email = email;
		this.eventId = eventId;
		this.isFavourite = isFavourite;
	}
	public FavouriteRequest() {
	}
	public String getEmail() {
		return email;
	}
	public String getEventId() {
		return eventId;
	}
	public boolean getIsFavourite() {
		return isFavourite;
	}
	
	

}