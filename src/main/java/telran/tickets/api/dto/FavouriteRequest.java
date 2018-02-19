package telran.tickets.api.dto;

public class FavouriteRequest {
	private String clientId;
	private String eventId;
	private boolean favourite;
	public FavouriteRequest(String clientId, String eventId, boolean favourite) {
		this.clientId = clientId;
		this.eventId = eventId;
		this.favourite = favourite;
	}
	public FavouriteRequest() {
	}
	public String getClientId() {
		return clientId;
	}
	public String getEventId() {
		return eventId;
	}
	public boolean isFavourite() {
		return favourite;
	}
	
	

}