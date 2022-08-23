package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.GameRepository;
import com.firsov.statistics_of_games_played.dto.GameDto;
import com.firsov.statistics_of_games_played.entity.Game;
import com.firsov.statistics_of_games_played.exception.GameAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public List<GameDto> getAllGameDto() {
        List<Game> games = gameRepository.findAll();

        return games.stream().map(game -> new GameDto(game.getId(), game.getName())).toList();
    }

    @Transactional
    public void saveNewGame(GameDto gameDto) {
        Game game = new Game(gameDto.getName());
        try {
            gameRepository.save(game);
        } catch (DataIntegrityViolationException e) {
            String error = "Произошла ошибка при добавлении игры - " + gameDto.getName()
                    +". Данная игра уже существует в базе данных";
            logger.warn(error, e);
            throw new GameAlreadyExistsException(error);
        }

    }

    public void deleteGame(int id) {
        gameRepository.deleteById(id);
    }
}
