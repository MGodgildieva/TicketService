package telran.tickets.entities.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class License {
	@Id
	private String license;
	
	public License() {
	}
	
	public License(String license) {
		this.license = license;
	}


	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	

}
