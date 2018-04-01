package telran.tickets.entities.users;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Confirmation {
	@Id
	public String code;
	public String info;
	public Confirmation(String code, String info) {
		this.code = code;
		this.info = info;
	}
	public Confirmation() {
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	

}
