package telran.tickets.api.dto;

public class AddOrganiser {
	private String email;
	private String password;
	private String companyName;
	private String phone;
	private String country;
	private String city;
	private String street;
	private String house;
	private String postcode;
	private String additionalInfo;
	public AddOrganiser() {
	}
	public AddOrganiser(String email, String password, String companyName, String phone, String country,
			String city, String street, String house, String postcode, String additionalInfo) {
		this.email = email;
		this.password = password;
		this.companyName = companyName;
		this.phone = phone;
		this.country = country;
		this.city = city;
		this.street = street;
		this.house = house;
		this.postcode = postcode;
		this.additionalInfo = additionalInfo;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getPhone() {
		return phone;
	}
	public String getCountry() {
		return country;
	}
	public String getCity() {
		return city;
	}
	public String getStreet() {
		return street;
	}
	public String getHouse() {
		return house;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	
	

}
