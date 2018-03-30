package telran.tickets.api.dto;

public class ReservationRequest {
	private String email;
	private String [] eventSeatIds;
	private boolean isBooked;
	public ReservationRequest() {
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String [] getEventSeatIds() {
		return eventSeatIds;
	}
	public void setEventSeatIds(String [] seatIds) {
		this.eventSeatIds = seatIds;
	}
	public boolean getIsBooked() {
		return isBooked;
	}
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}
	
	

}
