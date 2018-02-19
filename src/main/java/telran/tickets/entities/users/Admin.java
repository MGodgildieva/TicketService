package telran.tickets.entities.users;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String adminId;
	private String email;
	private String password;
	private String type;
	private static int adminCounter = 0;
	
	

	public Admin(String email, String password) {
		adminId = "A" + ++adminCounter;
		this.type = "Admin";
		this.email = email;
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAdminId() {
		return adminId;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminId == null) ? 0 : adminId.hashCode());
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
		if (!(obj instanceof Admin)) {
			return false;
		}
		Admin other = (Admin) obj;
		if (adminId == null) {
			if (other.adminId != null) {
				return false;
			}
		} else if (!adminId.equals(other.adminId)) {
			return false;
		}
		return true;
	}
	

}
