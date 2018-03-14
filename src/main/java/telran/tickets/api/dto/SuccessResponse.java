package telran.tickets.api.dto;

public class SuccessResponse {
	private boolean success;
	private String response;
	public SuccessResponse() {
	}
	
	public SuccessResponse(boolean success, String response) {
		this.success = success;
		this.response = response;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "SuccessResponse [success=" + success + ", response=" + response + "]";
	}
	
	

}
