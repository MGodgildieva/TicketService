package telran.tickets.api.dto;

public class HallRequest {
	private String orgId;
	private String hallId;
	private String[][] seats;
	public HallRequest(String orgId, String hallId, String[][] seats) {
		this.orgId = orgId;
		this.hallId = hallId;
		this.seats = seats;
	}
	public HallRequest() {
	}
	public String getOrgId() {
		return orgId;
	}
	public String getHallId() {
		return hallId;
	}
	public String[][] getSeats() {
		return seats;
	}
	
}
