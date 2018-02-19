package telran.tickets.api.dto;

public class OrgTypeRequest {
	private String orgId;
	private String type;
	public String getOrgId() {
		return orgId;
	}
	public String getType() {
		return type;
	}
	public OrgTypeRequest(String orgId, String type) {
		this.orgId = orgId;
		this.type = type;
	}
	public OrgTypeRequest() {
	}
	
	

}
