package telran.tickets.interfaces;

import java.io.IOException;
import java.util.Set;

import telran.tickets.api.dto.ClientBookedTicket;
import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.ClientTicket;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ReservationRequest;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.ShortRegisterClient;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketsRequest;

public interface IClient {
	SuccessResponse register (RegisterClient client);
	SuccessResponse register (ShortRegisterClient client);
	SuccessResponse checkConfirmation (String code) throws IOException;
	boolean bookTicket (ReservationRequest request);
	boolean buyTickets(TicketsRequest request) throws IOException;
	boolean addToFavourite (FavouriteRequest favRequest);
	Set<ShortEventInfo> getFavourite(String phone);
	ClientProfile getProfile(String phone);
	ClientProfile changeProfile(ClientProfile clientWithNewInfo);
	Iterable<ClientTicket> getBoughtTickets(String email);
	Iterable<ClientBookedTicket> getBookedTickets(String email);
	
}
