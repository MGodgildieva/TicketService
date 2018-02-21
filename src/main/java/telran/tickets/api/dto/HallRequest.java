package telran.tickets.api.dto;

public class HallRequest {
	private String email;
	private String hallId;
	private String[][] seats;
	public HallRequest(String email, String hallId, String[][] seats) {
		this.email = email;
		this.hallId = hallId;
		this.seats = seats;
	}

	public HallRequest() {
	}
	public String getEmail() {
		return email;
	}
	public String getHallId() {
		return hallId;
	}
	public String[][] getSeats() {
		return seats;
	}
	
}
