package telran.tickets.interfaces;

import java.io.IOException;
import java.util.Set;

import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.ClientTicket;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ReservationRequest;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.ShortRegisterClient;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketId;

public interface IClient {
	SuccessResponse register (RegisterClient client);
	SuccessResponse register (ShortRegisterClient client);
	SuccessResponse checkConfirmation (String code);
	TicketId bookTicket (ReservationRequest request);
	boolean checkOrder(Long orderId);
	boolean startPayment(boolean start, Long orderId);
	boolean finishPayment(Long orderId) throws IOException;
	boolean sendEmail(String email, Long orderId) throws IOException;
	//boolean buyTickets(TicketsRequest request) throws IOException;
	boolean addToFavourite (FavouriteRequest favRequest);
	Set<ShortEventInfo> getFavourite(String phone);
	ClientProfile getProfile(String phone);
	ClientProfile changeProfile(ClientProfile clientWithNewInfo);
	Iterable<ClientTicket> getBoughtTickets(String email);
	Iterable<ClientTicket> getBookedTickets(String email);
	boolean deleteTicket(Long orderId);
	public void checkSeat();
	
}
