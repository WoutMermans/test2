package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.repository.CrudRepository;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean isOutdoor);
    Iterable<Venue> findByIndoor(boolean isIndoor);
    Iterable<Venue> findByCapacityBetween(int min, int max);
    Iterable<Venue> findByCapacityGreaterThan(int min);
    Iterable<Venue> findByCapacityGreaterThanEqual(int min);
    Iterable<Venue> findByCapacityLessThanEqual(int max);
}
