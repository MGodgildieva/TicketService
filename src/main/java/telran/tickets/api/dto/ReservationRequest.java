package telran.tickets.api.dto;

public class ReservationRequest {
	private String phone;
	private String seatId;
	private boolean isBooked;
	public ReservationRequest() {
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	public boolean getIsBooked() {
		return isBooked;
	}
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}
	
	

}
