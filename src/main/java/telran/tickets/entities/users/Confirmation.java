package telran.tickets.entities.users;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Confirmation {
	@Id
	public String code;
	@Temporal(TemporalType.TIMESTAMP)
	public Date time;
	public String info;
	public Confirmation(String code, String info, Date date) {
		this.code = code;
		this.info = info;
		this.time = date;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	

}
