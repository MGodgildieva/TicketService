package telran.tickets;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.tickets.api.APIConstants;
import telran.tickets.api.dto.AddEvent;
import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.BanRequest;
import telran.tickets.api.dto.BuyingRequestNoReg;
import telran.tickets.api.dto.BuyingTicketsRequestNoReg;
import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.EditEvent;
import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.EventOrgRequest;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.HallRequest;
import telran.tickets.api.dto.HallScheme;
import telran.tickets.api.dto.LoginRequest;
import telran.tickets.api.dto.LoginResponse;
import telran.tickets.api.dto.OrgHallRequest;
import telran.tickets.api.dto.OrgTypeRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.RegisterOrganiser;
import telran.tickets.api.dto.ReservationRequest;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.ShortHallInfo;
import telran.tickets.api.dto.ShortRegisterClient;
import telran.tickets.api.dto.StringId;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketRequest;
import telran.tickets.api.dto.TicketsRequest;
import telran.tickets.api.dto.TypeRequest;
import telran.tickets.api.dto.VisibleRequest;
import telran.tickets.interfaces.IAdmin;
import telran.tickets.interfaces.IClient;
import telran.tickets.interfaces.IGeneral;
import telran.tickets.interfaces.IOrganiser;

@SpringBootApplication
@EnableScheduling
@RestController
@CrossOrigin
public class ServerAppl {
	@Autowired
	IClient clientRepository;
	@Autowired
	IAdmin adminRepository;
	@Autowired
	IOrganiser orgRepository;
	@Autowired
	IGeneral genRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ServerAppl.class, args);
	}

	// Client
	@PostMapping(APIConstants.CLIENT) 
	public SuccessResponse registerClient(@RequestBody RegisterClient request) {
		return clientRepository.register(request);
	}
	@PostMapping(APIConstants.CLIENT2) 
	public SuccessResponse registerClient(@RequestBody ShortRegisterClient request) {
		return clientRepository.register(request);
	}

	@GetMapping(APIConstants.FORGOTTEN_PASSWORD) 
	public SuccessResponse forgottenPassword(@RequestParam String email) {
		return  genRepository.forgottenPassword(email);
	}
	
	@PostMapping(APIConstants.BOOK_TICKET) 
	public boolean bookTicket(@RequestBody ReservationRequest request) {
		return clientRepository.bookTicket(request);
	}

	@PostMapping(APIConstants.BUY_TICKET) 
	public boolean buyTicket(@RequestBody TicketRequest request) {
		return clientRepository.buyTicket(request);
	}
	@PostMapping(APIConstants.BUY_TICKETS) 
	public boolean buyTickets(@RequestBody TicketsRequest request) throws IOException {
		return clientRepository.buyTickets(request);
	}

	@PostMapping(APIConstants.ADD_TO_FAVOURITE) 
	public boolean addToFavourite(@RequestBody FavouriteRequest request) {
		return clientRepository.addToFavourite(request);
	}

	@GetMapping(APIConstants.FAVOURITE) 
	public Iterable<ShortEventInfo> getFavourite(@RequestParam String email) {
		return clientRepository.getFavourite(email);
	}

	@GetMapping(APIConstants.CLIENT_PROFILE) 
	public ClientProfile getProfile(@RequestParam String email) {
		return clientRepository.getProfile(email);
	}

	@PutMapping(APIConstants.CHANGE_PROFILE) 
	public ClientProfile changeProfile(@RequestBody ClientProfile request) {
		return clientRepository.changeProfile(request);
	}

	// Organiser
	@PostMapping(APIConstants.ORG) 
	public SuccessResponse registerOrganiser(@RequestBody RegisterOrganiser request) {
		return orgRepository.register(request);
	}

	@GetMapping(APIConstants.ORG_EVENTS_BY_DATE) 
	public Iterable<ShortEventInfo> orgEventsByDate(@RequestParam String email) {
		return orgRepository.getEventsByDate(email);
	}

	@GetMapping(APIConstants.ORG_EVENTS_BY_HALL) 
	public Iterable<ShortEventInfo> orgEventsByHall(@RequestParam String email, @RequestParam String hallId) {
		return orgRepository.getEventsByHall(new OrgHallRequest(email, hallId));
	}

	@GetMapping(APIConstants.ORG_EVENTS_BY_TYPE) 
	public Iterable<ShortEventInfo> orgEventsByType(@RequestParam String email, @RequestParam String type) {
		return orgRepository.getEventsByType(new OrgTypeRequest(email, type));
	}

	@GetMapping(APIConstants.EDIT_EVENT) 
	public EditEvent editEvent(@RequestParam String email, @RequestParam String eventId) {
		return orgRepository.editEvent(new EventOrgRequest(email, eventId));
	}

	@PostMapping(APIConstants.ADD_EVENT) 
	public boolean addEvent(@RequestBody AddEvent request) throws Exception {
		return orgRepository.addEvent(request);
	}

	@PostMapping(APIConstants.HIDE_EVENT) 
	public boolean hideEvent(@RequestBody VisibleRequest request) {
		return orgRepository.hideEvent(request);
	}

	@DeleteMapping(APIConstants.EVENT) 
	public boolean deleteEvent(@RequestBody StringId eventId) {
		return orgRepository.deleteEvent(eventId.getId());
	}

	@GetMapping(APIConstants.ORG_PROFILE) 
	public RegisterOrganiser getOrganiserProfile(@RequestParam String email) {
		return orgRepository.getProfile(email);
	}

	@GetMapping(APIConstants.SEE_HALLS) 
	public Iterable<ShortHallInfo> getHalls(@RequestParam String email) {
		return orgRepository.getHalls(email);
	}

	@PostMapping(APIConstants.ADD_HALL) 
	public String addHall(@RequestBody HallRequest request) {
		return orgRepository.addHall(request);
	}
	
	@GetMapping(APIConstants.HALL_SCHEME) 
	public HallScheme getHallScheme(@RequestParam String hallId) {
		return orgRepository.getHallScheme(hallId);
	}
	

	// Admin
	
	@PostMapping(APIConstants.ADD_ORG) 
	public boolean addOrganiser(@RequestBody AddOrganiser request) {
		return adminRepository.addOrganiser(request);
	}

	@GetMapping(APIConstants.SEE_ORGANIZERS) 
	public Iterable<AddOrganiser> allOrganizers() {
		return adminRepository.getOrganisers();
	}

	@DeleteMapping(APIConstants.DELETE_ORGANIZER) 
	public boolean deleteOrganiser(@RequestBody StringId email) {
		return adminRepository.deleteOrganiser(email.getId());
	}

	@PostMapping(APIConstants.BAN_ORG) 
	public boolean banOrganiser(@RequestBody BanRequest request) {
		return adminRepository.banOrganiser(request);
	}
	@PostMapping(APIConstants.ADD_LICENSE) 
	public boolean addLicense(@RequestBody StringId license) {
		return adminRepository.addLicense(license.getId());
	}
	@DeleteMapping(APIConstants.CLEAN) 
	public boolean cleanDatabase() {
		return adminRepository.cleanDatabase();
	}
	
	//General

	@PostMapping(APIConstants.LOGIN) 
	public LoginResponse login(@RequestBody LoginRequest request) {
		return genRepository.login(request);
	}

	@GetMapping(APIConstants.EVENTS_BY_DATE) 
	public Iterable<ShortEventInfo> getEventsByDate(@RequestParam String city) {
		return genRepository.getEventsByDate(city);
	}
	
	@GetMapping(APIConstants.EVENTS_ON_DATE) 
	public Iterable<ShortEventInfo> getEventsOnDate(@RequestParam long date) throws ParseException {
		return genRepository.getEventsOnDate(date);
	}
	
	@GetMapping(APIConstants.EVENTS_IN_DATE_INTERVAL) 
	public Iterable<ShortEventInfo> getEventsByDate(@RequestParam long firstDate, @RequestParam long lastDate) throws ParseException {
		return genRepository.getEventsInDateInterval(firstDate, lastDate);
	}

	@GetMapping(APIConstants.EVENTS_BY_HALL) 
	public Iterable<ShortEventInfo> getEventsByPLace(@RequestParam String hallId) {
		return genRepository.getEventsByPlace(hallId);
	}

	@GetMapping(APIConstants.EVENTS_BY_TYPE) 
	public Iterable<ShortEventInfo> getEventsByType(@RequestParam String type, @RequestParam String city) {
		return genRepository.getEventsByType(new TypeRequest(city, type));
	}

	@GetMapping(APIConstants.ALL_CITIES) 
	public Iterable<String> getCities() {
		return genRepository.getCities();
	}
	@GetMapping(APIConstants.TYPES) 
	public Iterable<String> getTypes() {
		return genRepository.getTypes();
	}

	@GetMapping(APIConstants.HALLS_BY_CITY) 
	public Iterable<String> getHallsByCity(@RequestParam String city) {
		return genRepository.getHallsByCity(city);
	}

	@GetMapping(APIConstants.EVENT) 
	public FullEventInfo getEvent(@RequestParam String eventId, @RequestParam String email) {
		return genRepository.getEvent(new EventClientRequest(eventId, email));
	}

	@GetMapping(APIConstants.FULL_HALL) 
	public HallEventInfo getFullHall(@RequestParam String eventId) {
		return genRepository.getFullHall(eventId);
	}
	@PostMapping(APIConstants.BUY_TICKET_NO_REG) 
	public boolean buyTicket(@RequestBody BuyingRequestNoReg request) throws IOException {
		return genRepository.buyTicketWithoutRegistration(request);
	}
	@PostMapping(APIConstants.BUY_TICKETS_NO_REG) 
	public boolean buyTicket(@RequestBody BuyingTicketsRequestNoReg request) throws IOException {
		return genRepository.buyTicketsWithoutRegistration(request);
	}
	
	

}
