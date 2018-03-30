package telran.tickets.api.dto;

public class TicketsRequest {
	private String email;
	private String eventId;
	private String [] eventSeatId;
	
	public TicketsRequest(String email, String eventId, String [] eventSeatId) {
		this.email = email;
		this.eventId = eventId;
		this.eventSeatId = eventSeatId;
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
		return eventSeatId;
	}
}
