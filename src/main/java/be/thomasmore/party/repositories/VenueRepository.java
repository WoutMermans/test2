package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean isOutdoor);
    Iterable<Venue> findByIndoor(boolean isIndoor);
    Iterable<Venue> findByCapacityBetween(int min, int max);
    Iterable<Venue> findByCapacityGreaterThan(int min);
    Iterable<Venue> findByCapacityGreaterThanEqual(int min);
    Iterable<Venue> findByCapacityLessThanEqual(int max);

    @Query("SELECT v FROM Venue v " +
            "WHERE (:min IS NULL OR v.capacity >= :min) " +
            "AND (:max IS NULL OR v.capacity <= :max)")
    Iterable<Venue> findByCapacityBetweenQuery(
            @Param("min") Integer min, @Param("max") Integer max);

    @Query("SELECT v FROM Venue v " +
            "WHERE (:min IS NULL OR v.capacity >= :min) " +
            "AND (:max IS NULL OR v.capacity <= :max) " +
            "AND (:distance IS NULL OR v.distanceFromPublicTransportInKm <= :distance) " +
            "AND (:food IS NULL OR v.foodProvided = :food) " +
            "AND (:indoor IS NULL OR v.indoor = :indoor) " +
            "AND (:outdoor IS NULL OR v.outdoor = :outdoor) ")
    List<Venue> findByCapacityDistanceFoodIndoorOutdoor(
            @Param("min") Integer min, @Param("max") Integer max,
            @Param("distance") Double distance,
            @Param("food") Boolean food,
            @Param("indoor") Boolean indoor,
            @Param("outdoor") Boolean outdoor);
}
