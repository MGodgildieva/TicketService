package telran.tickets;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import telran.tickets.api.dto.BookedTickets;
import telran.tickets.api.dto.BoughtTickets;
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
import telran.tickets.api.dto.ShortEventInfos;
import telran.tickets.api.dto.ShortHallInfo;
import telran.tickets.api.dto.ShortRegisterClient;
import telran.tickets.api.dto.StringId;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketsRequest;
import telran.tickets.api.dto.TypeRequest;
import telran.tickets.api.dto.VisibleRequest;
import telran.tickets.errors.DatabaseError;
import telran.tickets.errors.EmailError;
import telran.tickets.errors.JsonError;
import telran.tickets.errors.RegistrationError;
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
	@SuppressWarnings("rawtypes")
	@PostMapping(APIConstants.CLIENT) 
	public ResponseEntity registerClient(@RequestBody RegisterClient request) {
		try {
			return ResponseEntity.ok(clientRepository.register(request));
		}catch(EmailError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SuccessResponse(false, e.getMessage()));
		}catch(DatabaseError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse(false, e.getMessage()));
		}catch(RegistrationError e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new SuccessResponse(false, e.getMessage()));
		}catch(JsonError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse(false, e.getMessage()));
		}
	}
	@SuppressWarnings("rawtypes")
	@PostMapping(APIConstants.CLIENT2) 
	public ResponseEntity registerClient(@RequestBody ShortRegisterClient request) {
		try {
			return ResponseEntity.ok(clientRepository.register(request));
		}catch(EmailError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SuccessResponse(false, e.getMessage()));
		}catch(DatabaseError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse(false, e.getMessage()));
		}catch(RegistrationError e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new SuccessResponse(false, e.getMessage()));
		}catch(JsonError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse(false, e.getMessage()));
		}
		
	}
	@SuppressWarnings("rawtypes")
	@GetMapping(APIConstants.CONFIRMATION)
	public ResponseEntity checkConfirmation (@RequestParam String code) {
		try {
			return ResponseEntity.ok(clientRepository.checkConfirmation(code));
		}catch(DatabaseError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse(false, e.getMessage()));
		}catch(RegistrationError e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new SuccessResponse(false, e.getMessage()));
		}catch(JsonError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse(false, e.getMessage()));
		}
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(APIConstants.FORGOTTEN_PASSWORD) 
	public ResponseEntity forgottenPassword(@RequestParam String email) {
		try {
			return ResponseEntity.ok(genRepository.forgottenPassword(email));
		}catch(EmailError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SuccessResponse(false, e.getMessage()));
		}catch(DatabaseError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SuccessResponse(false, e.getMessage()));
		}catch(RegistrationError e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new SuccessResponse(false, e.getMessage()));
		}
	}
	
	@PostMapping(APIConstants.BOOK_TICKET) 
	public boolean bookTicket(@RequestBody ReservationRequest request) {
		return clientRepository.bookTicket(request);
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
	public ShortEventInfos getFavourite(@RequestParam String email) {
		return new ShortEventInfos(clientRepository.getFavourite(email));
	}

	@GetMapping(APIConstants.CLIENT_PROFILE) 
	public ClientProfile getProfile(@RequestParam String email) {
		return clientRepository.getProfile(email);
	}

	@PutMapping(APIConstants.CHANGE_PROFILE) 
	public ClientProfile changeProfile(@RequestBody ClientProfile request) {
		return clientRepository.changeProfile(request);
	}
	@GetMapping(APIConstants.CLIENT_TICKETS)
	public BoughtTickets getBoughtTickets(@RequestParam String email) {
		return new BoughtTickets(clientRepository.getBoughtTickets(email));
	}
	@GetMapping(APIConstants.CLIENT_BOOKED_TICKETS)
	public BookedTickets getBookedTickets(@RequestParam String email) {
		return new BookedTickets(clientRepository.getBookedTickets(email));
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

	@SuppressWarnings("rawtypes")
	@PostMapping(APIConstants.LOGIN) 
	public ResponseEntity login(@RequestBody LoginRequest request) {
		try {
			return ResponseEntity.ok(genRepository.login(request));
		} catch (DatabaseError e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse(e.getMessage()));
		}catch (RegistrationError e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new LoginResponse(e.getMessage()));
		}
	}

	@GetMapping(APIConstants.EVENTS_BY_DATE) 
	public ShortEventInfos getEventsByDate(@RequestParam String city) {
		return new ShortEventInfos(genRepository.getEventsByDate(city));
	}
	
	@GetMapping(APIConstants.EVENTS_ON_DATE) 
	public ShortEventInfos getEventsOnDate(@RequestParam long date) throws ParseException {
		return new ShortEventInfos(genRepository.getEventsOnDate(date));
	}
	
	@GetMapping(APIConstants.EVENTS_IN_DATE_INTERVAL) 
	public ShortEventInfos getEventsByDate(@RequestParam long firstDate, @RequestParam long lastDate) throws ParseException {
		return new ShortEventInfos(genRepository.getEventsInDateInterval(firstDate, lastDate));
	}

	@GetMapping(APIConstants.EVENTS_BY_HALL) 
	public ShortEventInfos getEventsByPLace(@RequestParam String hallId) {
		return new ShortEventInfos(genRepository.getEventsByPlace(hallId));
	}

	@GetMapping(APIConstants.EVENTS_BY_TYPE) 
	public ShortEventInfos getEventsByType(@RequestParam String type, @RequestParam String city) {
		return new ShortEventInfos(genRepository.getEventsByType(new TypeRequest(city, type)));
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
	@PostMapping(APIConstants.BUY_TICKETS_NO_REG) 
	public boolean buyTicket(@RequestBody TicketsRequest request) throws IOException {
		return genRepository.buyTicketsWithoutRegistration(request);
	}
	@GetMapping(APIConstants.SEARCH)
	public ShortEventInfos searchEvents(@RequestParam String text) {
		return new ShortEventInfos(genRepository.searchEvents(text));
	}
	
	

}
