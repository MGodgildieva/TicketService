package telran.tickets.interfaces;

import java.util.Set;

import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.TicketRequest;

public interface IClient {
	String register (RegisterClient client);
	String forgottenPassword(String email);
	boolean buyTicket(TicketRequest ticket);
	boolean addToFavourite (FavouriteRequest favRequest);
	Set<ShortEventInfo> getFavourite(String email);
	ClientProfile getProfile(String email);
	ClientProfile changeProfile(ClientProfile clientWithNewInfo);
	
}
