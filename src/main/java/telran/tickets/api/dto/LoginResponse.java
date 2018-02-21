package telran.tickets.api.dto;

public class LoginResponse {
	private String email;
	private String type;
	public LoginResponse(String email, String type) {
		this.email = email;
		this.type = type;
	}
	public LoginResponse() {
	}
	public String getEmail() {
		return email;
	}
	public String getType() {
		return type;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
