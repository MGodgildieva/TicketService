package telran.tickets.api.dto;

public class RegisterClient {
	String email;
	String password;
	String country;
	String city;
	String street;
	String house;
	String postcode;
	String additionalInfo;
	String phone;
	String name;
	String surname;
	String honour;
	String additionalPhone;
	String company;
	public RegisterClient(String email, String password, String country, String city, String street,
			String house, String postcode, String additionalInfo, String phone, String name, String surname,
			String honour, String additionalPhone, String company) {
		this.email = email;
		this.password = password;
		this.country = country;
		this.city = city;
		this.street = street;
		this.house = house;
		this.postcode = postcode;
		this.additionalInfo = additionalInfo;
		this.phone = phone;
		this.name = name;
		this.surname = surname;
		this.honour = honour;
		this.additionalPhone = additionalPhone;
		this.company = company;
	}
	public RegisterClient() {
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
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
	public String getPhone() {
		return phone;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getHonour() {
		return honour;
	}
	public String getAdditionalPhone() {
		return additionalPhone;
	}
	public String getCompany() {
		return company;
	}
	
	

}
