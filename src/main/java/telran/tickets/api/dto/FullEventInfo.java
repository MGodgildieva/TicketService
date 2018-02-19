package telran.tickets.api.dto;

public class FullEventInfo {
	private String orgId;
	private String artist;
	private String title;
	private String date;
	private String time;
	private String ticketCount;
	private String description;
	private String priceRange;
	private String imageUrl;
	private boolean favourite;
	public FullEventInfo(String organiserId, String artist, String title, String date, String time, String ticketCount,
			String description, String priceRange, String imageUrl, boolean favourite) {
		this.orgId = organiserId;
		this.artist = artist;
		this.title = title;
		this.date = date;
		this.time = time;
		this.ticketCount = ticketCount;
		this.description = description;
		this.priceRange = priceRange;
		this.imageUrl = imageUrl;
		this.favourite = favourite;
	}
	public FullEventInfo() {
	}
	public String getOrganiserId() {
		return orgId;
	}
	public String getArtist() {
		return artist;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	public String getTicketCount() {
		return ticketCount;
	}
	public String getDescription() {
		return description;
	}
	public String getPriceRange() {
		return priceRange;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public boolean isFavourite() {
		return favourite;
	}
	
	

}
