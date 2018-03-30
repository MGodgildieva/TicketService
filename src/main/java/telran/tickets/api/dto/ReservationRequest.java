package telran.tickets.api.dto;

public class ReservationRequest {
	private String email;
	private String eventSeatId;
	private boolean isBooked;
	public ReservationRequest() {
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSeatId() {
		return eventSeatId;
	}
	public void setSeatId(String seatId) {
		this.eventSeatId = seatId;
	}
	public boolean getIsBooked() {
		return isBooked;
	}
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}
	
	

}
