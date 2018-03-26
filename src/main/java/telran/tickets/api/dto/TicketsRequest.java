package telran.tickets.api.dto;

public class TicketsRequest {
	private String phone;
	private String eventId;
	private String [] eventSeatId;
	
	public TicketsRequest(String phone, String eventId, String [] eventSeatId) {
		this.phone = phone;
		this.eventId = eventId;
		this.eventSeatId = eventSeatId;
	}
	public TicketsRequest() {
	}
	public String getPhone() {
		return phone;
	}
	
	public String getEventId() {
		return eventId;
	}
	public String [] getEventSeatIds() {
		return eventSeatId;
	}
}
