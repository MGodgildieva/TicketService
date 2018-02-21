package telran.tickets.api.dto;

import telran.tickets.entities.users.Client;

public class ClientProfile {
	String email;
	String name;
	String surname;
	String honour;
	String type;
	String password;
	String country;
	String city;
	String street;
	String house;
	String postcode;
	String additionalInfo;
	String phone;
	String additionalPhone;
	String company;
	public ClientProfile(String email, String name, String surname, String honour, String type, String password,
			String country, String city, String street, String house, String postcode, String additionalInfo,
			String phone, String additionalPhone, String company) {
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.honour = honour;
		this.type = type;
		this.password = password;
		this.country = country;
		this.city = city;
		this.street = street;
		this.house = house;
		this.postcode = postcode;
		this.additionalInfo = additionalInfo;
		this.phone = phone;
		this.additionalPhone = additionalPhone;
		this.company = company;
	}
	public ClientProfile() {
	}
	
	public ClientProfile (Client client) {
		this.email = client.getEmail();
		this.name = client.getName();
		this.surname = client.getSurname();
		this.honour = client.getHonour();
		this.type = client.getType();
		this.password = client.getPassword();
		this.country = client.getCountry();
		this.city = client.getCity();
		this.street = client.getStreet();
		this.house = client.getHouse();
		this.postcode = client.getPostcode();
		this.additionalInfo = client.getAdditionalInfo();
		this.phone = client.getPhone();
		this.additionalPhone = client.getAdditionalPhone();
		this.company = client.getCompany();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getHonour() {
		return honour;
	}
	public void setHonour(String honour) {
		this.honour = honour;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAdditionalPhone() {
		return additionalPhone;
	}
	public void setAdditionalPhone(String additionalPhone) {
		this.additionalPhone = additionalPhone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	

}
