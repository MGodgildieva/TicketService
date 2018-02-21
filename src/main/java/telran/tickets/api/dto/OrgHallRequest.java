package telran.tickets.api.dto;

public class OrgHallRequest {
	private String email;
	private String hallId;
	public OrgHallRequest(String email, String hallId) {
		this.email = email;
		this.hallId = hallId;
	}
	public OrgHallRequest() {
	}
	public String getEmail() {
		return email;
	}
	public String getHallId() {
		return hallId;
	}
	
	

}
