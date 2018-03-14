package telran.tickets.api.dto;

public class LoginRequest {
	private String id;
	private String password;
	
	public LoginRequest(String id, String password) {
		this.id = id;
		this.password = password;
	}
	public LoginRequest() {
	}
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	
	
	
}
