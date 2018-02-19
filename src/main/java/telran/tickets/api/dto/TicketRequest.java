package telran.tickets.api.dto;

public class TicketRequest {
	private String hallId;
	private String clientId;
	private String eventId;
	private String ticketPrice;
	private String row;
	private String place;
	
	public TicketRequest(String hallId, String clientId, String eventId, String ticketPrice, String row, String place) {
		this.hallId = hallId;
		this.clientId = clientId;
		this.eventId = eventId;
		this.ticketPrice = ticketPrice;
		this.row = row;
		this.place = place;
	}
	public TicketRequest() {
	}
	public String getClientId() {
		return clientId;
	}
	
	public String getHallId() {
		return hallId;
	}
	public String getEventId() {
		return eventId;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public String getRow() {
		return row;
	}
	public String getPlace() {
		return place;
	}
	
	
}
