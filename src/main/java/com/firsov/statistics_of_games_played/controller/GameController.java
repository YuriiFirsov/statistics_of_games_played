package com.firsov.statistics_of_games_played.controller;

import com.firsov.statistics_of_games_played.dto.GameDto;
import com.firsov.statistics_of_games_played.exception.GameAlreadyExistsException;
import com.firsov.statistics_of_games_played.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class GameController {


    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/add_game")
    public String addGame(Model model) {
        GameDto gameDto = new GameDto();
        model.addAttribute("game", gameDto);
        return "/add_game";
    }

    @PostMapping("/save_new_game")
    public String saveNewGame(@ModelAttribute("game") @Valid GameDto gameDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "/add_game";
        } else {
            try {
                gameService.saveNewGame(gameDto);
            } catch (GameAlreadyExistsException e) {
                model.addAttribute("error", e.getMessage());
                return "/errors";
            }

            return "redirect:/index";
        }
    }

    @GetMapping("/delete_game")
    public String delete(@RequestParam("gameId") int id) {
        gameService.deleteGame(id);
        return "redirect:/index";
    }
}
