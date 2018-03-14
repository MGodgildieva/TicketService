package telran.tickets.api.dto;

public class VisibleRequest {
	private String eventId;
	private boolean isHidden;

	public VisibleRequest(String eventId, boolean isHidden) {
		this.eventId = eventId;
		this.isHidden = isHidden;
	}

	public VisibleRequest() {
	}

	public boolean getIsHidden() {
		return isHidden;
	}

	public String getEventId() {
		return eventId;
	}

}
