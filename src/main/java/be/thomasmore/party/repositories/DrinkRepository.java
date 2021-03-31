package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Drink;
import be.thomasmore.party.model.Party;
import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrinkRepository extends CrudRepository<Drink, Integer> {
    Iterable<Drink> findByLight(boolean isLight);
    Iterable<Drink> findByPriceBetween(int min, int max);
    Iterable<Drink> findByAlcoholic(boolean isAlcoholic);

    @Query("SELECT v FROM Drink v " +
            "WHERE (:min IS NULL OR v.price >= :min) " +
            "AND (:max IS NULL OR v.price <= :max)")
    Iterable<Drink> findByPriceBetweenQuery(
            @Param("min") Integer min, @Param("max") Integer max);

    @Query("SELECT v FROM Drink v " +
            "WHERE (:min IS NULL OR v.price >= :min) " +
            "AND (:max IS NULL OR v.price <= :max) " +
            "AND (:light IS NULL OR v.light = :light) " +
            "AND (:alcoholic IS NULL OR v.alcoholic = :alcoholic) ")
    List<Drink> findByPriceLightAlcoholic(
            @Param("min") Integer min,
            @Param("max") Integer max,
            @Param("light") Boolean light,
            @Param("alcoholic") Boolean alcoholic);

}
