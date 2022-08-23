package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.PlayerRepository;
import com.firsov.statistics_of_games_played.dto.InfoPlayerDto;
import com.firsov.statistics_of_games_played.dto.PlayerDto;
import com.firsov.statistics_of_games_played.entity.Player;
import com.firsov.statistics_of_games_played.entity.Result;
import com.firsov.statistics_of_games_played.exception.PlayerAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;


    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public List<InfoPlayerDto> getAllPlayerDto() {
        List<Player> players = playerRepository.findAll();

        return players.stream().map(this::convertToInfoPlayerDtoByNameGame).sorted(InfoPlayerDto::compareByCount).toList();
    }

    @Transactional
    public List<InfoPlayerDto> getScorePlayers(String selectedGame) {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .map(player -> convertToInfoPlayerDtoByNameGame(player, selectedGame))
                .filter(playerDto -> playerDto.getCount() != 0)
                .sorted().toList();
    }

    @Transactional
    public void savePlayer(PlayerDto playerDto) {
        Player player = new Player(playerDto.getUsername(), playerDto.getName());
        try {
            playerRepository.save(player);
        } catch (DataIntegrityViolationException e) {
            String error = "Произошла ошибка при добавлении игрока с  username - " + player.getUsername()
                    + " Данный username уже существует в базе данных";
            logger.warn(error, e);
            throw new PlayerAlreadyExistsException(error);

        }
    }

    @Transactional
    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
    }

    private InfoPlayerDto convertToInfoPlayerDtoByNameGame(Player player) {
        InfoPlayerDto infoPlayerDto = new InfoPlayerDto(player.getId(), player.getUsername(), player.getName());
        infoPlayerDto.setCount(player.getResults().size());
        int numberOfPointsScored = 0;
        for (Result result : player.getResults()) {
            numberOfPointsScored += result.getNumberOfPointsPerGame();
        }
        infoPlayerDto.setScore(numberOfPointsScored);
        return infoPlayerDto;
    }

    private InfoPlayerDto convertToInfoPlayerDtoByNameGame(Player player, String selectedGame) {
        InfoPlayerDto playerDto = new InfoPlayerDto(player.getId(), player.getUsername(), player.getName());

        int numberOfPointsScored = 0;
        int count = 0;
        for (Result result : player.getResults()) {
            if (result.getPartyToTheGame().getGame().getName().equals(selectedGame)) {
                count++;
                numberOfPointsScored += result.getNumberOfPointsPerGame();
            }
        }
        playerDto.setCount(count);
        playerDto.setScore(numberOfPointsScored);
        return playerDto;
    }

}
