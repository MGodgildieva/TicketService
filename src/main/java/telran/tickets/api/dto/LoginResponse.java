package telran.tickets.api.dto;

public class LoginResponse {
	private boolean success;
	private String error;
	private String email;
	private String type;
	public LoginResponse(String email, String type) {
		this.success = true;
		this.email = email;
		this.type = type;
	}
	
	public LoginResponse(String error) {
		this.success = false;
		this.error = error;
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
}
