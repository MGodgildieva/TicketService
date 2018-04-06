package telran.tickets.api;

public interface APIConstants {
	public static final String ADD = "/add";
	public static final String EVENTS = "/events";
	public static final String DATE = "/date";
	public static final String TYPE = "/type";
	public static final String HALL = "/hall";
	public static final String PROFILE = "/profile";
	
	public static final String CLIENT = "/client";
	public static final String CLIENT2 = "/client2";
	public static final String CONFIRMATION = "/confirmation";
	public static final String LOGIN = "/login";
	public static final String FORGOTTEN_PASSWORD = "/password";
	public static final String EVENTS_BY_DATE= EVENTS + DATE;
	public static final String EVENTS_BY_HALL = EVENTS + HALL;
	public static final String EVENTS_BY_TYPE = EVENTS + TYPE;
	public static final String EVENTS_ON_DATE= EVENTS + "/on" + DATE;
	public static final String EVENTS_IN_DATE_INTERVAL= EVENTS + "/in" + DATE + "s";
	public static final String ALL_CITIES = "/cities";
	public static final String HALLS_BY_CITY = "/halls/city";
	public static final String EVENT = "/event";
	public static final String FULL_HALL = EVENT + HALL;
	public static final String BOOK_TICKET = "/booking";
	public static final String BUY_TICKET = "/ticket";
	public static final String BUY_TICKETS = BUY_TICKET + "s";
	public static final String BUY_TICKET_NO_REG = BUY_TICKET + "/noreg";
	public static final String BUY_TICKETS_NO_REG = BUY_TICKETS + "/noreg";
	public static final String ADD_TO_FAVOURITE = ADD + "/favourite";
	public static final String LANG = "/lang";
	public static final String FAVOURITE = "/favourite";
	public static final String CLIENT_PROFILE = CLIENT + PROFILE;
	public static final String CHANGE_PROFILE = PROFILE;
	public static final String ORG = "/organiser";
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
	public static final String HALL_SCHEME = "/hallscheme";
	public static final String ADD_ORG = ADD + ORG;
	public static final String SEE_ORGANIZERS = "/organisers";
	public static final String DELETE_ORGANIZER = ORG;
	public static final String BAN_ORG = "/ban" + ORG;
	public static final String ADD_LICENSE = ADD + "/license";
	public static final String CLEAN = "/clean";
	public static final String TYPES = TYPE + "s";
	public static final String CLIENT_TICKETS = CLIENT + BUY_TICKETS;
	public static final String CLIENT_BOOKED_TICKETS = CLIENT + BOOK_TICKET + BUY_TICKETS;
	public static final String SEARCH = "/search";
	
	
	
	
}
