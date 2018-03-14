package telran.tickets.api.dto;

public class FavouriteRequest {
	private String phone;
	private String eventId;
	private boolean isFavourite;
	public FavouriteRequest(String phone, String eventId, boolean isFavourite) {
		this.phone = phone;
		this.eventId = eventId;
		this.isFavourite = isFavourite;
	}
	public FavouriteRequest() {
	}
	public String getPhone() {
		return phone;
	}
	public String getEventId() {
		return eventId;
	}
	public boolean getIsFavourite() {
		return isFavourite;
	}
	
	

}