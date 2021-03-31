package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Drink;
import be.thomasmore.party.repositories.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class DrinkController {

    @Autowired
    private DrinkRepository drinkRepository;

    @GetMapping("/drinklist")
    public String artistList(Model model) {
        Iterable<Drink> drinks = drinkRepository.findAll();
        model.addAttribute("drinks", drinks);
        model.addAttribute("nrDrinks", drinkRepository.count());
        return "drinklist";
    }

    @GetMapping("/drinklist/filter")
    public String drinkListWithFilter(Model model,
                                      @RequestParam(required = false) Integer minimumPrice,
                                      @RequestParam(required = false) Integer maximumPrice,
                                      @RequestParam(required = false) String light,
                                      @RequestParam(required = false) String alcoholic) {
        List<Drink> drinks = drinkRepository.findByPriceLightAlcoholic(minimumPrice, maximumPrice,
                ((light==null || light.equals("all")) ? null : (light.equals("yes") ? true : false)),
                ((alcoholic==null || alcoholic.equals("all")) ? null : (alcoholic.equals("yes") ? true : false)));

        model.addAttribute("maxPrice", maximumPrice);
        model.addAttribute("minPrice", minimumPrice);
        model.addAttribute("light", light);
        model.addAttribute("alcoholic", alcoholic);
        model.addAttribute("nrDrinks", drinks.size());
        model.addAttribute("showFilter", true);
        return "drinklist";
    }
}
