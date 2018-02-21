package telran.tickets.api.dto;

public class EventOrgRequest {
	private String email;
	private String eventId;
	public String getEmail() {
		return email;
	}
	public String getEventId() {
		return eventId;
	}
	public EventOrgRequest(String email, String eventId) {
		this.email = email;
		this.eventId = eventId;
	}
	public EventOrgRequest() {
	}
	
	

}
