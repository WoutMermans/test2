package be.thomasmore.party.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Drink {
    @Id
    private int id;
    private String name;
    private boolean light;
    private boolean alcoholic;
    private double price;
    @ManyToMany(mappedBy = "drinks")
    private Collection<Venue> venues;

    public Drink(){
    }

    public Collection<Venue> getVenues() {
        return venues;
    }

    public void setVenues(Collection<Venue> venues) {
        this.venues = venues;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}