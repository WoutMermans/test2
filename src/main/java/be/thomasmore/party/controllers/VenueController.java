package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.PartyRepository;
import be.thomasmore.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class VenueController {
    private Logger logger = LoggerFactory.getLogger(VenueController.class);
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private PartyRepository partyRepository;

    @GetMapping({"/venuelist", "/venuelist/{what}"})
    public String venueList(Model model, @PathVariable(required = false) String what) {
        Iterable<Venue> venues = venueRepository.findAll();
        model.addAttribute("venues", venues);
        model.addAttribute("nrVenues", venueRepository.count());
        return "venuelist";
    }

    @GetMapping("/venuelist/filter")
    public String venueListWithFilter(Model model,
                                      @RequestParam(required = false) Integer minimumCapacity,
                                      @RequestParam(required = false) Integer maximumCapacity,
                                      @RequestParam(required = false) Double distance,
                                      @RequestParam(required = false) String foodProvided,
                                      @RequestParam(required = false) String indoor,
                                      @RequestParam(required = false) String outdoor) {
        List<Venue> venues = venueRepository.findByCapacityDistanceFoodIndoorOutdoor(minimumCapacity, maximumCapacity, distance,
                ((foodProvided==null || foodProvided.equals("all")) ? null : (foodProvided.equals("yes") ? true : false)),
                ((indoor==null || indoor.equals("all")) ? null : (indoor.equals("yes") ? true : false)),
                ((outdoor==null || outdoor.equals("all")) ? null : (outdoor.equals("yes") ? true : false)));
        model.addAttribute("maxCapacity", maximumCapacity);
        model.addAttribute("minCapacity", minimumCapacity);
        model.addAttribute("distance", distance);
        model.addAttribute("foodProvided", foodProvided);
        model.addAttribute("indoor", indoor);
        model.addAttribute("outdoor", outdoor);
        model.addAttribute("venues", venues);
        model.addAttribute("nrVenues", venues.size());
        model.addAttribute("showFilter", true);
        return "venuelist";
    }

    @GetMapping({"/venuedetails", "/venuedetails/{id}"})
    public String venueDetails(Model model, @PathVariable(required = false) Integer id) {
        Venue venue = null;
        Integer prev = null;
        Integer next = null;
        if (venueRepository.findById(id).isPresent()) {
            venue = venueRepository.findById(id).get();
            model.addAttribute("parties", partyRepository.findByVenue(venue));
            prev = id>1 ? id-1 : (int)venueRepository.count();
            next = id<venueRepository.count() ? id+1 : 1;
        }
        model.addAttribute("venue", venue);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        return "venuedetails";
    }
}
