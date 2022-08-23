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
import java.util.stream.Collectors;

@Service
public class PlayerService {

    Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;


    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public List<PlayerDto> getAllPlayerDto() {
        return playerRepository.findAll().stream().map(this::convertToPlayerDto).collect(Collectors.toList());
    }

    /**
     * метод возвращает список игроков отсортированный по суммарному количеству сыгранных игр
     * для отображения на главной странице
     */
    @Transactional
    public List<InfoPlayerDto> getAllInfoPlayerDto() {
        List<Player> players = playerRepository.findAll();

        return players.stream().map(this::convertToInfoPlayerDto).sorted(InfoPlayerDto::compareByCount).toList();
    }

    /**
     * метод возвращает список игроков отсортированный по количеству набранных очков в выбранной игре
     */
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

    private PlayerDto convertToPlayerDto(Player player) {
        return new PlayerDto(player.getId(), player.getUsername(), player.getName());
    }

    /**
     * метод конвертирует переданного player в infoPlayerDto и считает количество всех сыгранных игр
     */
    private InfoPlayerDto convertToInfoPlayerDto(Player player) {
        InfoPlayerDto infoPlayerDto = new InfoPlayerDto(player.getId(), player.getUsername(), player.getName());
        infoPlayerDto.setCount(player.getResults().size());
        int numberOfPointsScored = 0;
        for (Result result : player.getResults()) {
            numberOfPointsScored += result.getNumberOfPointsPerGame();
        }
        infoPlayerDto.setScore(numberOfPointsScored);
        return infoPlayerDto;
    }

    /**
     * метод конвертирует переданного player в infoPlayerDto,
     * а так же считает количество всех сыгранных игр и набранных очков в конкретной игре
     */
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
