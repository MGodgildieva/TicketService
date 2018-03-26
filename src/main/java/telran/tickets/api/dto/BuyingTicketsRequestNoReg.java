package telran.tickets.api.dto;

public class BuyingTicketsRequestNoReg {
	String email;
	String eventId;
	String [] eventSeatIds;
	public BuyingTicketsRequestNoReg() {
	}
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getEventSeatIds() {
		return eventSeatIds;
	}
	public void setEventSeatIds(String[] eventSeatIds) {
		this.eventSeatIds = eventSeatIds;
	}
	

}
