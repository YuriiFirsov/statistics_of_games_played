package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.GameRepository;
import com.firsov.statistics_of_games_played.dto.GameDto;
import com.firsov.statistics_of_games_played.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<GameDto> getAllGameDto(){
        List<Game> games = gameRepository.findAll();

        return games.stream().map(game -> new GameDto(game.getId(), game.getName())).toList();
    }

    public List<Game> getAllGame() {
        return gameRepository.findAll();
    }

    public void saveNewGame(GameDto gameDto) {
        Game game = new Game(gameDto.getName());
        gameRepository.save(game);
    }

    public void deleteGame(int id) {
        gameRepository.deleteById(id);
    }
}
