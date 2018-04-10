package telran.tickets.api.dto;

public class TicketsRequest {
	private String email;
	private String eventId;
	private String [] eventSeatIds;
	//private Long ticketId;
	
	public TicketsRequest(String email, String eventId, String [] eventSeatIds) {
		this.email = email;
		this.eventId = eventId;
		this.eventSeatIds = eventSeatIds;
		//this.ticketId = ticketId;
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
	/*public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}*/
	
}
