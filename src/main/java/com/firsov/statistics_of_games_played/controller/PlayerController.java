package com.firsov.statistics_of_games_played.controller;

import com.firsov.statistics_of_games_played.dto.PlayerDto;
import com.firsov.statistics_of_games_played.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PlayerController {

    @Autowired
    PlayerService playerService = new PlayerService();

    @GetMapping("/add_player")
    public String addPlayer(Model model) {
        PlayerDto playerDto = new PlayerDto();
        model.addAttribute("player", playerDto);
        return "/add_player";
    }

    @PostMapping("save_new_player")
    public String saveNewPlayer(@ModelAttribute("player") @Valid PlayerDto playerDto
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/add_player";
        } else {
            playerService.savePlayer(playerDto);
            return "redirect:/index";
        }
    }

    @GetMapping("/delete_player")
    public String deletePlayer(@RequestParam("playerId") int id) {
        playerService.deletePlayer(id);
        return "redirect:/index";
    }

}
