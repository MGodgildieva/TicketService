package telran.tickets.api.dto;

public class ShortEventInfos {
	Iterable<ShortEventInfo> events;

	public ShortEventInfos(Iterable<ShortEventInfo> events) {
		this.events = events;
	}

	public ShortEventInfos() {
	}

	public Iterable<ShortEventInfo> getEvents() {
		return events;
	}

	public void setEvents(Iterable<ShortEventInfo> events) {
		this.events = events;
	}
	
	

}
