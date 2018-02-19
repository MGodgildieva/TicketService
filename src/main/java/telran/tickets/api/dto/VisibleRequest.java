package telran.tickets.api.dto;

public class VisibleRequest {
	private String eventId;
	private boolean isVisible;

	public VisibleRequest(String eventId, boolean isVisible) {
		this.eventId = eventId;
		this.isVisible = isVisible;
	}

	public VisibleRequest() {
	}

	public boolean isVisible() {
		return isVisible;
	}

	public String getEventId() {
		return eventId;
	}

}
