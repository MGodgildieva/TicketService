package telran.tickets.api.dto;

public class TicketsRequest {
	private String email;
	private String eventId;
	private String [] eventSeatIds;
	
	public TicketsRequest(String email, String eventId, String [] eventSeatIds) {
		this.email = email;
		this.eventId = eventId;
		this.eventSeatIds = eventSeatIds;
	}
	public TicketsRequest() {
	}
	public String getEmail() {
		return email;
	}
	
	public String getEventId() {
		return eventId;
	}
	public String [] getEventSeatIds() {
		return eventSeatIds;
	}
}
