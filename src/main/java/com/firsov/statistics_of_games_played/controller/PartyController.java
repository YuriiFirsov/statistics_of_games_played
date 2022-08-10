package com.firsov.statistics_of_games_played.controller;

import com.firsov.statistics_of_games_played.dto.NewPartyDto;
import com.firsov.statistics_of_games_played.dto.PartyDto;
import com.firsov.statistics_of_games_played.dto.PlayerDto;
import com.firsov.statistics_of_games_played.entity.Game;
import com.firsov.statistics_of_games_played.service.GameService;
import com.firsov.statistics_of_games_played.service.PartyToTheGameService;
import com.firsov.statistics_of_games_played.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PartyController {

    @Autowired
    private PartyToTheGameService partyService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @GetMapping("/add_party")
    public String addParty(Model model) {
        NewPartyDto newPartyDto = new NewPartyDto();
        List<Game> games = gameService.getAllGame();
        List<PlayerDto> players = playerService.getAllPlayerDto();

        model.addAttribute("party", newPartyDto);
        model.addAttribute("games", games);
        model.addAttribute("players", players);
        return "/add_party";
    }

    @PostMapping("save_new_party")
    public String saveNewParty(@ModelAttribute("party") @Valid NewPartyDto newPartyDto
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/add_party";
        } else {
            partyService.saveNewParty(newPartyDto);
            return "redirect:/index";
        }
    }

    @GetMapping("/delete_party")
    public String deleteParty(@RequestParam("partyId") int id) {
        partyService.deletePartyToTheGame(id);
        return "redirect:/index";
    }

}
