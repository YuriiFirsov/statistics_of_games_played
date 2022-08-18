package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.PlayerRepository;
import com.firsov.statistics_of_games_played.dao.ResultRepository;
import com.firsov.statistics_of_games_played.dto.PlayerDto;
import com.firsov.statistics_of_games_played.entity.Player;
import com.firsov.statistics_of_games_played.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerService {

    Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, ResultRepository resultRepository) {
        this.playerRepository = playerRepository;
        this.resultRepository = resultRepository;
    }

    public List<PlayerDto> getAllPlayerDto() {
        List<Result> results = resultRepository.findAll();
        List<Player> players = playerRepository.findAll();
        List<PlayerDto> playerDtoList = getListPlayerDto(results);

        for (Player player : players) {
            if (!playerDtoList.contains(convertToPlayerDto(player))) {
                playerDtoList.add(new PlayerDto(player.getId(), player.getUsername()
                        , player.getName(), 0, 0));
            }
        }

        return playerDtoList.stream().sorted(PlayerDto::compareByCount).collect(Collectors.toList());
    }

    public List<PlayerDto> getScorePlayers(String selectedGame) {
        List<Result> results = resultRepository.findAllByPartyToTheGame_Game_Name(selectedGame);

        return getListPlayerDto(results).stream().sorted().collect(Collectors.toList());
    }

    public void savePlayer(PlayerDto playerDto) {
        Player player = new Player(playerDto.getUsername(), playerDto.getName());
        try {
            playerRepository.save(player);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Попытка добавить игрока с существующим username. Username " + player.getUsername()
                    + " уже существует в базе данных");

        }
    }

    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
    }

    public PlayerDto convertToPlayerDto(Player player) {
        return new PlayerDto(player.getId(), player.getUsername(), player.getName());
    }

    public List<PlayerDto> getListPlayerDto(List<Result> results) {
        List<PlayerDto> playerDtoList = new ArrayList<>();

        for (Result result : results) {
            if (!playerDtoList.contains(convertToPlayerDto(result.getPlayer()))) {
                playerDtoList.add(new PlayerDto(result.getPlayer().getId(), result.getPlayer().getUsername()
                        , result.getPlayer().getName(), result.getNumberOfPointsPerGame(), 1));


            } else {
                playerDtoList
                        .stream()
                        .filter(playerDto -> playerDto.getId() == result.getPlayer().getId())
                        .forEach(playerDto -> {
                            playerDto.setScore(playerDto.getScore() + result.getNumberOfPointsPerGame());
                            playerDto.setCount(playerDto.getCount() + 1);
                        });
            }
        }

        return playerDtoList;
    }


}
