package telran.tickets.api.dto;

public class EventOrgRequest {
	private String orgId;
	private String eventId;
	public String getOrgId() {
		return orgId;
	}
	public String getEventId() {
		return eventId;
	}
	public EventOrgRequest(String orgId, String eventId) {
		this.orgId = orgId;
		this.eventId = eventId;
	}
	public EventOrgRequest() {
	}
	
	

}
