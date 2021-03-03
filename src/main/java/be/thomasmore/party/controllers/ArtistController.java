package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.ArtistRepository;
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
public class ArtistController {
    private Logger logger = LoggerFactory.getLogger(ArtistController.class);
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artistlist")
    public String artistList(Model model) {
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        model.addAttribute("nrArtists", artistRepository.count());
        return "artistlist";
    }

    @GetMapping("/artistlist/filter")
    public String artistListWithFilter(Model model, @RequestParam(required = false) String keyword) {
        logger.info("artistListWithFilter -- keyword=" + keyword);
        model.addAttribute("keyword", keyword);
        List<Artist> artists = artistRepository.findByKeyword(keyword);
        long nrArtists = artists.size();
        model.addAttribute("artists", artists);
        model.addAttribute("nrArtists", nrArtists);
        model.addAttribute("showFilter", true);
        return "artistlist";
    }

    @GetMapping({"/artistdetails", "/artistdetails/{id}"})
    public String artistDetails(Model model, @PathVariable(required = false) Integer id) {
        Artist artist = null;
        Integer prev = null;
        Integer next = null;
        if (artistRepository.findById(id).isPresent()) {
            artist = artistRepository.findById(id).get();
            prev = id>1 ? id-1 : (int)artistRepository.count();
            next = id<artistRepository.count() ? id+1 : 1;
        }
        model.addAttribute("artist", artist);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        return "artistdetails";
    }
}
