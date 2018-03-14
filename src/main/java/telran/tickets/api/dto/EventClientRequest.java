package telran.tickets.api.dto;

public class EventClientRequest {
	private String eventId;
	private String phone;
	public String getEventId() {
		return eventId;
	}
	public String getPhone() {
		return phone;
	}
	public EventClientRequest(String eventId, String phone) {
		this.eventId = eventId;
		this.phone = phone;
	}
	public EventClientRequest() {
	}
	
	

}
