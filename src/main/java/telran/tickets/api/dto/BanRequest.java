package telran.tickets.api.dto;

public class BanRequest {
	private String orgId;
	private boolean isBanned;
	public BanRequest(String orgId, boolean isBanned) {
		this.orgId = orgId;
		this.isBanned = isBanned;
	}
	public BanRequest() {
	}
	public String getOrgId() {
		return orgId;
	}
	public boolean isBanned() {
		return isBanned;
	}
	
	

}
