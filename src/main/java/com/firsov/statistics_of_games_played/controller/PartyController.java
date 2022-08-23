package com.firsov.statistics_of_games_played.controller;

import com.firsov.statistics_of_games_played.dto.*;
import com.firsov.statistics_of_games_played.service.GameService;
import com.firsov.statistics_of_games_played.service.PartyToTheGameService;
import com.firsov.statistics_of_games_played.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PartyController {

    private final PartyToTheGameService partyService;
    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public PartyController(PartyToTheGameService partyService, GameService gameService, PlayerService playerService) {
        this.partyService = partyService;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/add_party")
    public String addParty(Model model) {
        InfoPartyDto infoPartyDto = partyService.creatingAnEmptyInfoPartyDto();
        List<GameDto> games = gameService.getAllGameDto();
        List<PlayerDto> players = playerService.getAllPlayerDto();

        model.addAttribute("party", infoPartyDto);
        model.addAttribute("games", games);
        model.addAttribute("players", players);
        return "/party_info";
    }

    @PostMapping("save_new_party")
    public String saveNewParty(@ModelAttribute("party") InfoPartyDto infoPartyDto) {
        partyService.saveOrUpdateParty(infoPartyDto);
        return "redirect:/index";
    }

    @GetMapping("/delete_party")
    public String deleteParty(@RequestParam("partyId") int id) {
        partyService.deletePartyToTheGame(id);
        return "redirect:/index";
    }

    @GetMapping("/update_party")
    public String updateParty(@RequestParam("partyId") int id, Model model) {
        List<GameDto> games = gameService.getAllGameDto();
        List<PlayerDto> players = playerService.getAllPlayerDto();
        InfoPartyDto infoPartyDto = partyService.collectionInfoPartyDto(id);

        model.addAttribute("games", games);
        model.addAttribute("players", players);
        model.addAttribute("party", infoPartyDto);

        return "/party_info";
    }

}
