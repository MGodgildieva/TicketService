package telran.tickets.api.dto;

public class ShortRegisterClient {
	String phone;
	String password;
	
	
	public ShortRegisterClient() {
	}
	public ShortRegisterClient(String phone, String password) {
		this.phone = phone;
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
