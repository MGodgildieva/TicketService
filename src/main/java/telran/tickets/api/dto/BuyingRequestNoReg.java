package telran.tickets.api.dto;

public class BuyingRequestNoReg {
	String email;
	String eventSeatId;
	public BuyingRequestNoReg(String email, String eventSeatId) {
		this.email = email;
		this.eventSeatId = eventSeatId;
	}
	public BuyingRequestNoReg() {
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEventSeatId() {
		return eventSeatId;
	}
	public void setEventSeatId(String eventSeatId) {
		this.eventSeatId = eventSeatId;
	}
	
	

}
