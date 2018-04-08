package telran.tickets.entities.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class License {
	@Id
	private String license;
	private String email;
	
	public License() {
	}
	
	public License(String license, String email) {
		this.license = license;
		this.email = email;
	}


	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
