package telran.tickets.api.dto;

public class TicketRequest {
	private String phone;
	private String eventId;
	private String eventSeatId;
	
	public TicketRequest(String phone, String eventId, String eventSeatId) {
		this.phone = phone;
		this.eventId = eventId;
		this.eventSeatId = eventSeatId;
	}
	public TicketRequest() {
	}
	public String getPhone() {
		return phone;
	}
	
	public String getEventId() {
		return eventId;
	}
	public String getEventSeatId() {
		return eventSeatId;
	}
	
	
	
}
