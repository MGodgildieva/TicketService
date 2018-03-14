package telran.tickets.entities.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.RegisterOrganiser;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.Hall;

@Entity
public class Organiser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	String email;
	String companyName;
	String license;
	String type;
	String password;
	String country;
	String city;
	String street;
	String house;
	String postcode;
	String additionalInfo;
	String phone;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "org", orphanRemoval = true)
	Set<Event> events;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "org")
	
	Set<Hall> halls;
	Boolean isBanned;

	public Organiser(String companyName, String license, String email, String password, String country, String city,
			String street, String house, String postcode, String additionalInfo, String phone, Boolean isBanned) {
		this.companyName = companyName;
		this.license = license;
		this.type = "Org";
		this.email = email;
		this.password = password;
		this.country = country;
		this.city = city;
		this.street = street;
		this.house = house;
		this.postcode = postcode;
		this.additionalInfo = additionalInfo;
		this.phone = phone;
		this.events = new HashSet<>();
		this.halls = new HashSet<>();
		this.isBanned = isBanned;
	}

	public Organiser(RegisterOrganiser org) {
		this.companyName = org.getCompanyName();
		this.license = org.getLicense();
		this.type = "Org";
		this.email = org.getEmail();
		this.password = org.getPassword();
		this.country = org.getCountry();
		this.city = org.getCity();
		this.street = org.getStreet();
		this.house = org.getHouse();
		this.postcode = org.getPostcode();
		this.additionalInfo = org.getAdditionalInfo();
		this.phone = org.getPhone();
		this.events = new HashSet<>();
		this.halls = new HashSet<>();
		this.isBanned = false;
	}
	public Organiser(AddOrganiser org) {
		this.companyName = org.getCompanyName();
		this.type = "Org";
		this.email = org.getEmail();
		this.password = org.getPassword();
		this.country = org.getCountry();
		this.city = org.getCity();
		this.street = org.getStreet();
		this.house = org.getHouse();
		this.postcode = org.getPostcode();
		this.additionalInfo = org.getAdditionalInfo();
		this.phone = org.getPhone();
		this.events = new HashSet<>();
		this.halls = new HashSet<>();
		this.isBanned = false;
	}

	public Organiser() {
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public Set<Hall> getHalls() {
		return halls;
	}

	public void setHalls(Set<Hall> halls) {
		this.halls = halls;
	}

	public Boolean getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		if (!(obj instanceof Organiser)) {
			return false;
		}
		Organiser other = (Organiser) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		return true;
	}

}
