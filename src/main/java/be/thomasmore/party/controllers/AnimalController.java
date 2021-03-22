package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Animal;
import be.thomasmore.party.model.Artist;
import be.thomasmore.party.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AnimalController {
    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping({"/animaldetails", "/animaldetails/{id}"})
    public String animalDetails(Model model, @PathVariable(required = false) Integer id) {
        Animal animal = null;
        Integer prev = null;
        Integer next = null;
        if (animalRepository.findById(id).isPresent()) {
            animal = animalRepository.findById(id).get();
            prev = id>1 ? id-1 : (int)animalRepository.count();
            next = id<animalRepository.count() ? id+1 : 1;
        }
        model.addAttribute("animal", animal);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        return "animaldetails";
    }
}