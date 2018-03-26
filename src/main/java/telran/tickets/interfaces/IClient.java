package telran.tickets.interfaces;

import java.io.IOException;
import java.util.Set;

import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ReservationRequest;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.ShortRegisterClient;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketRequest;
import telran.tickets.api.dto.TicketsRequest;

public interface IClient {
	SuccessResponse register (RegisterClient client);
	SuccessResponse register (ShortRegisterClient client);
	boolean bookTicket (ReservationRequest request);
	boolean buyTicket(TicketRequest ticket);
	boolean buyTickets(TicketsRequest request) throws IOException;
	boolean addToFavourite (FavouriteRequest favRequest);
	Set<ShortEventInfo> getFavourite(String phone);
	ClientProfile getProfile(String phone);
	ClientProfile changeProfile(ClientProfile clientWithNewInfo);
	
}
