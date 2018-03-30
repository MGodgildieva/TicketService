package telran.tickets.api.dto;

public class EventClientRequest {
	private String eventId;
	private String email;
	public String getEventId() {
		return eventId;
	}
	public String getEmail() {
		return email;
	}
	public EventClientRequest(String eventId, String email) {
		this.eventId = eventId;
		this.email = email;
	}
	public EventClientRequest() {
	}
	
	

}
