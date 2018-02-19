package telran.tickets.api.dto;

public class EventClientRequest {
	private String eventId;
	private String clientId;
	public String getEventId() {
		return eventId;
	}
	public String getEmail() {
		return clientId;
	}
	public EventClientRequest(String eventId, String clientId) {
		this.eventId = eventId;
		this.clientId = clientId;
	}
	public EventClientRequest() {
	}
	
	

}
