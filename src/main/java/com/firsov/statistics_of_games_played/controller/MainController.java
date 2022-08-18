package com.firsov.statistics_of_games_played.controller;

import com.firsov.statistics_of_games_played.dto.GameDto;
import com.firsov.statistics_of_games_played.dto.PartyDto;
import com.firsov.statistics_of_games_played.dto.PlayerDto;
import com.firsov.statistics_of_games_played.service.GameService;
import com.firsov.statistics_of_games_played.service.PartyToTheGameService;
import com.firsov.statistics_of_games_played.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final GameService gameService;
    private final PlayerService playerService;
    private final PartyToTheGameService partyToTheGameService;

    @Autowired
    public MainController(GameService gameService, PlayerService playerService,
                          PartyToTheGameService partyToTheGameService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.partyToTheGameService = partyToTheGameService;
    }

    @GetMapping("/index")
    public String showAll(Model model) {
        List<GameDto> games = gameService.getAllGameDto();
        List<PlayerDto> players = playerService.getAllPlayerDto();
        List<PartyDto> parties = partyToTheGameService.getAllPartiesDto();

        model.addAttribute("games", games);
        model.addAttribute("players", players);
        model.addAttribute("parties", parties);

        return "index";
    }

    @GetMapping("/show_info_selected_game")
    public String showGame(Model model, @RequestParam("selectedGame") String selectedGame) {
        List<PlayerDto> players = playerService.getScorePlayers(selectedGame);
        List<PartyDto> parties = partyToTheGameService.getAllPartiesDtoSelectedGame(selectedGame);

        model.addAttribute("players", players);
        model.addAttribute("parties", parties);

        return "/show_info_selected_game";
    }

}
