package telran.tickets.entities.users;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import telran.tickets.api.dto.RegisterClient;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.Ticket;

@Entity
public class Client implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	String clientId;
	static Integer clientCounter = 0;
	String name;
	String surname;
	String honour;
	String type;
	String email;
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
	@ManyToMany
	Set<Event> favourite;
	@OneToMany
	Set<Ticket> boughtTickets;

	public Client(String name, String surname, String honour, String email, String password,
			String country, String city, String street, String house, String postcode, String additionalInfo,
			String phone, String additionalPhone, String company, Set<Event> favourite, Set<Ticket> boughtTickets) {
		this.clientId = "C" + (++clientCounter);
		this.name = name;
		this.surname = surname;
		this.honour = honour;
		this.type = "Client";
		this.email = email;
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
		this.favourite = favourite;
		this.boughtTickets = boughtTickets;
	}

	public Client(RegisterClient client) {
		this.clientId = "C" + (++clientCounter);
		this.type = "Client";
		this.email = client.getEmail();
		this.password = client.getPassword();
		this.country = client.getCountry();
		this.city = client.getCity();
		this.street = client.getStreet();
		this.house = client.getHouse();
		this.postcode = client.getPostcode();
		this.additionalInfo = client.getAdditionalInfo();
		this.phone = client.getPhone();
		this.name = client.getName();
		this.surname = client.getSurname();
		this.honour = client.getHonour();
		this.additionalPhone = client.getAdditionalPhone();
		this.company = client.getCompany();

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Set<Event> getFavourite() {
		return favourite;
	}

	public void setFavourite(Set<Event> favourite) {
		this.favourite = favourite;
	}

	public Set<Ticket> getBoughtTickets() {
		return boughtTickets;
	}

	public void setBoughtTickets(Set<Ticket> boughtTickets) {
		this.boughtTickets = boughtTickets;
	}

	public String getClientId() {
		return clientId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Client)) {
			return false;
		}
		Client other = (Client) obj;
		if (clientId == null) {
			if (other.clientId != null) {
				return false;
			}
		} else if (!clientId.equals(other.clientId)) {
			return false;
		}
		return true;
	}

}
