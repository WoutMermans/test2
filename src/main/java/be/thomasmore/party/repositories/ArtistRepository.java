package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
    Iterable<Artist> findByArtistNameContainingIgnoreCase(String keyword);
    @Query("SELECT a FROM Artist a " +
            "WHERE ?1 IS NULL " +
            "OR LOWER(a.artistName) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(a.bio) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(a.portfolio) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Artist> findByKeyword(String keyword);
}