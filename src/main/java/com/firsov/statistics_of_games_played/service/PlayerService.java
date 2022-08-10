package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.PlayerRepository;
import com.firsov.statistics_of_games_played.dao.ResultRepository;
import com.firsov.statistics_of_games_played.dto.PlayerDto;
import com.firsov.statistics_of_games_played.entity.Player;
import com.firsov.statistics_of_games_played.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ResultRepository resultRepository;

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
        playerRepository.save(player);
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
                for (PlayerDto player : playerDtoList) {
                    if (player.getId() == result.getPlayer().getId()) {
                        player.setScore(player.getScore() + result.getNumberOfPointsPerGame());
                        player.setCount(player.getCount() + 1);
                        break;
                    }
                }
            }
        }

        return playerDtoList;
    }


}
