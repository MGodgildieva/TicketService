package telran.tickets.interfaces;

import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.BanRequest;

public interface IAdmin {
	boolean addOrganiser(AddOrganiser organiser);
	Iterable<AddOrganiser> getOrganisers();
	boolean deleteOrganiser(String email);
	boolean banOrganiser(BanRequest banRequest);

}
