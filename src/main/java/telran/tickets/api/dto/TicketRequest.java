package telran.tickets.api.dto;

public class TicketRequest {
	private String email;
	private String eventId;
	private String eventSeatId;
	
	public TicketRequest(String email, String eventId, String eventSeatId) {
		this.email = email;
		this.eventId = eventId;
		this.eventSeatId = eventSeatId;
	}
	public TicketRequest() {
	}
	public String getEmail() {
		return email;
	}
	
	public String getEventId() {
		return eventId;
	}
	public String getEventSeatId() {
		return eventSeatId;
	}
	
	
	
}
