package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Party;
import be.thomasmore.party.repositories.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PartyController {
    @Autowired
    private PartyRepository partyRepository;

    @GetMapping("/partylist")
    public String partyList(Model model) {
        Iterable<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        model.addAttribute("nrParties", partyRepository.count());
        return "partylist";
    }

    @GetMapping({"/partydetails", "/partydetails/{id}"})
    public String partyDetails(Model model, @PathVariable(required = false) Integer id) {
        Party party = null;
        Integer prev = null;
        Integer next = null;
        if (partyRepository.findById(id).isPresent()) {
            party = partyRepository.findById(id).get();
            prev = id>1 ? id-1 : (int)partyRepository.count();
            next = id<partyRepository.count() ? id+1 : 1;
        }
        model.addAttribute("party", party);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        return "partydetails";
    }
}
