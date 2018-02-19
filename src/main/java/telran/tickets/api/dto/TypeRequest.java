package telran.tickets.api.dto;

public class TypeRequest {
	private String city;
	private String type;
	public TypeRequest(String city, String type) {
		this.city = city;
		this.type = type;
	}
	public TypeRequest() {
	}
	public String getCity() {
		return city;
	}
	public String getType() {
		return type;
	}
	
	

}
