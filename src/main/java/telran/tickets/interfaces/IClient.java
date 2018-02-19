package telran.tickets.interfaces;

import java.util.Set;

import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.TicketRequest;
import telran.tickets.entities.users.Client;

public interface IClient {
	String register (RegisterClient client);
	String forgottenPassword(String email);
	boolean buyTicket(TicketRequest ticket);
	boolean addToFavourite (FavouriteRequest favRequest);
	Set<ShortEventInfo> getFavourite(String clientId);
	Client getProfile(String clientId);
	Client changeProfile(Client clientWithNewInfo);
	
}
