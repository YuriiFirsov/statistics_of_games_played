package com.firsov.statistics_of_games_played.controller;

import com.firsov.statistics_of_games_played.dto.GameDto;
import com.firsov.statistics_of_games_played.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class GameController {

    @Autowired
    GameService gameService = new GameService();

    @GetMapping("/add_game")
    public String addGame(Model model) {
        GameDto gameDto = new GameDto();
        model.addAttribute("game", gameDto);
        return "/add_game";
    }

    @PostMapping("/save_new_game")
    public String saveNewGame(@ModelAttribute("game") @Valid GameDto gameDto
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/add_game";
        } else {
            gameService.saveNewGame(gameDto);
            return "redirect:/index";
        }
    }

    @GetMapping("/delete_game")
    public String delete(@RequestParam("gameId") int id) {
        gameService.deleteGame(id);
        return "redirect:/index";
    }
}
