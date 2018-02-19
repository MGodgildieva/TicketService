package telran.tickets.api.dto;

public class OrgHallRequest {
	private String orgId;
	private String hallId;
	public OrgHallRequest(String orgId, String hallId) {
		this.orgId = orgId;
		this.hallId = hallId;
	}
	public OrgHallRequest() {
	}
	public String getOrgId() {
		return orgId;
	}
	public String getHallId() {
		return hallId;
	}
	
	

}
