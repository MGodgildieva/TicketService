package telran.tickets;

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
import org.springframework.web.bind.annotation.RestController;

import telran.tickets.api.APIConstants;
import telran.tickets.api.dto.AddEvent;
import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.BanRequest;
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
import telran.tickets.api.dto.StringId;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketRequest;
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

	@PostMapping(APIConstants.FORGOTTEN_PASSWORD) 
	public SuccessResponse forgottenPassword(@RequestBody StringId id) {
		return  genRepository.forgottenPassword(id.getId());
	}
	
	@PostMapping(APIConstants.BOOK_TICKET) 
	public boolean bookTicket(@RequestBody ReservationRequest request) {
		return clientRepository.bookTicket(request);
	}

	@PostMapping(APIConstants.BUY_TICKET) 
	public boolean buyTicket(@RequestBody TicketRequest request) {
		return clientRepository.buyTicket(request);
	}

	@PostMapping(APIConstants.ADD_TO_FAVOURITE) 
	public boolean addToFavourite(@RequestBody FavouriteRequest request) {
		return clientRepository.addToFavourite(request);
	}

	@PostMapping(APIConstants.FAVOURITE) 
	public Iterable<ShortEventInfo> getFavourite(@RequestBody StringId email) {
		return clientRepository.getFavourite(email.getId());
	}

	@PostMapping(APIConstants.CLIENT_PROFILE) 
	public ClientProfile getProfile(@RequestBody StringId email) {
		return clientRepository.getProfile(email.getId());
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

	@PostMapping(APIConstants.ORG_EVENTS_BY_DATE) 
	public Iterable<ShortEventInfo> orgEventsByDate(@RequestBody StringId email) {
		return orgRepository.getEventsByDate(email.getId());
	}

	@PostMapping(APIConstants.ORG_EVENTS_BY_HALL) 
	public Iterable<ShortEventInfo> orgEventsByHall(@RequestBody OrgHallRequest request) {
		return orgRepository.getEventsByHall(request);
	}

	@PostMapping(APIConstants.ORG_EVENTS_BY_TYPE) 
	public Iterable<ShortEventInfo> orgEventsByType(@RequestBody OrgTypeRequest request) {
		return orgRepository.getEventsByType(request);
	}

	@PostMapping(APIConstants.EDIT_EVENT) 
	public EditEvent editEvent(@RequestBody EventOrgRequest request) {
		return orgRepository.editEvent(request);
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

	@PostMapping(APIConstants.ORG_PROFILE) 
	public RegisterOrganiser getOrganiserProfile(@RequestBody StringId email) {
		return orgRepository.getProfile(email.getId());
	}

	@PostMapping(APIConstants.SEE_HALLS) 
	public Iterable<ShortHallInfo> getHalls(@RequestBody StringId email) {
		return orgRepository.getHalls(email.getId());
	}

	@PostMapping(APIConstants.ADD_HALL) 
	public String addHall(@RequestBody HallRequest request) {
		return orgRepository.addHall(request);
	}
	
	@PostMapping(APIConstants.HALL_SCHEME) 
	public HallScheme getHallScheme(@RequestBody StringId hallId) {
		return orgRepository.getHallScheme(hallId.getId());
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

	@PostMapping(APIConstants.EVENTS_BY_DATE) 
	public Iterable<ShortEventInfo> getEventsByDate(@RequestBody StringId city) {
		return genRepository.getEventsByDate(city.getId());
	}

	@PostMapping(APIConstants.EVENTS_BY_HALL) 
	public Iterable<ShortEventInfo> getEventsByPLace(@RequestBody StringId hallId) {
		return genRepository.getEventsByPlace(hallId.getId());
	}

	@PostMapping(APIConstants.EVENTS_BY_TYPE) 
	public Iterable<ShortEventInfo> getEventsByType(@RequestBody TypeRequest request) {
		return genRepository.getEventsByType(request);
	}

	@GetMapping(APIConstants.ALL_CITIES) 
	public Iterable<String> getCities() {
		return genRepository.getCities();
	}

	@PostMapping(APIConstants.HALLS_BY_CITY) 
	public Iterable<String> getHallsByCity(@RequestBody StringId city) {
		return genRepository.getHallsByCity(city.getId());
	}

	@PostMapping(APIConstants.EVENT) 
	public FullEventInfo getEvent(@RequestBody EventClientRequest request) {
		return genRepository.getEvent(request);
	}

	@PostMapping(APIConstants.FULL_HALL) 
	public HallEventInfo getFullHall(@RequestBody StringId eventId) {
		return genRepository.getFullHall(eventId.getId());
	}
	
	

}
