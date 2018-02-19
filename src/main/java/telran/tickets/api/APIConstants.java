package telran.tickets.api;

public interface APIConstants {
	public static final String ADD = "/add";
	public static final String EVENTS = "/events";
	public static final String DATE = "/date";
	public static final String PLACE = "/place";
	public static final String TYPE = "/type";
	public static final String HALL = "/hall";
	public static final String PROFILE = "/profile";
	
	public static final String CLIENT = "/client";
	public static final String LOGIN = "/login";
	public static final String FORGOTTEN_PASSWORD = "/password";
	public static final String EVENTS_BY_DATE= EVENTS + DATE;
	public static final String EVENTS_BY_PLACE = EVENTS + PLACE;
	public static final String EVENTS_BY_TYPE = EVENTS + TYPE;
	public static final String ALL_CITIES = "/cities";
	public static final String PLACES_BY_CITY = "/places";
	public static final String EVENT = "/event";
	public static final String FULL_HALL = EVENT + HALL;
	public static final String BUY_TICKET = "/ticket";
	public static final String ADD_TO_FAVOURITE = ADD + "/favourite";
	public static final String LANG = "/lang";
	public static final String FAVOURITE = "/favourite";
	public static final String INFO = "/info";
	public static final String CLIENT_PROFILE = CLIENT + PROFILE;
	public static final String CHANGE_PROFILE = PROFILE;
	public static final String ORG = "/organizer";
	public static final String ORG_EVENTS_BY_DATE = ORG + EVENTS + DATE;
	public static final String ORG_EVENTS_BY_TYPE = ORG + EVENTS + TYPE;
	public static final String ORG_EVENTS_BY_HALL = ORG + EVENTS + HALL;
	public static final String HIDE_EVENT = "/hide";
	public static final String EDIT_EVENT = "/edit";
	public static final String ADD_EVENT = ADD + EVENT;
	public static final String DELETE_EVENT = EVENT;
	public static final String ORG_PROFILE = ORG + PROFILE;
	public static final String SEE_HALLS = "/halls";
	public static final String ADD_HALL = ADD + HALL;
	public static final String ADD_ORG = ADD + ORG;
	public static final String SEE_ORGANIZERS = "/organizers";
	public static final String DELETE_ORGANIZER = ORG;
	public static final String BAN_ORG = "/ban" + ORG;
	
	
	
	
}
