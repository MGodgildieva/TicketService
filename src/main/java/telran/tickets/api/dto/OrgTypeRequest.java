package telran.tickets.api.dto;

public class OrgTypeRequest {
	private String email;
	private String type;
	public String getEmail() {
		return email;
	}
	public String getType() {
		return type;
	}
	public OrgTypeRequest(String email, String type) {
		this.email = email;
		this.type = type;
	}
	public OrgTypeRequest() {
	}
	
	

}
