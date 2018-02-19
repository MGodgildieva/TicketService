package telran.tickets.api.dto;

public class LoginResponse {
	private String id;
	private String type;
	public LoginResponse(String id, String type) {
		this.id = id;
		this.type = type;
	}
	public LoginResponse() {
	}
	public String getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	
}
