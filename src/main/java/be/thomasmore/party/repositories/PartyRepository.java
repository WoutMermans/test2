package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Party;
import org.springframework.data.repository.CrudRepository;

public interface PartyRepository extends CrudRepository<Party, Integer>  {
}
