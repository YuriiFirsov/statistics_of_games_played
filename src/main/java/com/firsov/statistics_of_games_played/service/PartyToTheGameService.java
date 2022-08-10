package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.PartyToTheGameRepository;
import com.firsov.statistics_of_games_played.dao.PlayerRepository;
import com.firsov.statistics_of_games_played.dao.ResultRepository;
import com.firsov.statistics_of_games_played.dto.NewPartyDto;
import com.firsov.statistics_of_games_played.dto.PartyDto;
import com.firsov.statistics_of_games_played.dto.ResultDto;
import com.firsov.statistics_of_games_played.entity.Game;
import com.firsov.statistics_of_games_played.entity.PartyToTheGame;

import com.firsov.statistics_of_games_played.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartyToTheGameService {

    @Autowired
    private PartyToTheGameRepository partyToTheGameRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameService gameService;

    public List<PartyDto> getAllPartiesDto() {

        List<Result> results = resultRepository.findAll();

        return getListPartyDto(results);
    }

    public List<PartyDto> getAllPartiesDtoSelectedGame(String selectedGame) {

        List<Result> results = resultRepository.findAllByPartyToTheGame_Game_Name(selectedGame);

        return getListPartyDto(results);
    }

    public void saveNewParty(NewPartyDto newPartyDto) {
        List<Game> games = gameService.getAllGame();

        PartyToTheGame partyToTheGame = new PartyToTheGame();

        for (Game game : games) {
            if (game.getName().equals(newPartyDto.getGameName())) {
                partyToTheGame.setGame(game);
                break;
            }
        }

        if (!Objects.equals(newPartyDto.getDate(), "")) {
            partyToTheGame.setDate(newPartyDto.getDate());
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            LocalDateTime date = LocalDateTime.now();
            partyToTheGame.setDate(date.format(formatter));
        }

        partyToTheGameRepository.save(partyToTheGame);

        partyToTheGame = partyToTheGameRepository.findTopByOrderByIdDesc();

        recordANewResult(partyToTheGame, newPartyDto.getPlayer1(), newPartyDto.getScore1());
        recordANewResult(partyToTheGame, newPartyDto.getPlayer2(), newPartyDto.getScore2());
        recordANewResult(partyToTheGame, newPartyDto.getPlayer3(), newPartyDto.getScore3());
        recordANewResult(partyToTheGame, newPartyDto.getPlayer4(), newPartyDto.getScore4());

    }

    public void deletePartyToTheGame(int id) {

        resultRepository.deleteAllByPartyToTheGame_Id(id);

        PartyToTheGame partyToTheGame = partyToTheGameRepository.findById(id);
        partyToTheGameRepository.delete(partyToTheGame);
    }

    public void recordANewResult(PartyToTheGame partyToTheGame, String player, int score) {
        if (!player.equals("Выберите имя")) {
            Result result = new Result();
            result.setPartyToTheGame(partyToTheGame);
            result.setPlayer(playerRepository.findByUsername(player));
            result.setNumberOfPointsPerGame(score);
            resultRepository.save(result);
        }
    }

    public List<PartyDto> getListPartyDto(List<Result> results) {
        List<List<Result>> listOfResultsGroupedByParty = results.stream()
                .collect(Collectors.groupingBy(r -> r.getPartyToTheGame().getId(), TreeMap::new, Collectors.toList()))
                .values().stream().toList();

        List<PartyDto> allPartyDto = new ArrayList<>();

        listOfResultsGroupedByParty.forEach(results1 -> {
            PartyDto gameDto = new PartyDto();

            PartyToTheGame partyToTheGame = results1.get(0).getPartyToTheGame();

            gameDto.setId(partyToTheGame.getId());
            gameDto.setGameName(partyToTheGame.getGame().getName());
            gameDto.setPartyDate(partyToTheGame.getDate());

            List<ResultDto> resultDtoList = results1
                    .stream()
                    .map(result ->
                            new ResultDto(
                                    result.getPlayer().getId(),
                                    result.getPlayer().getUsername(),
                                    result.getNumberOfPointsPerGame()
                            )).toList();
            gameDto.setResultDtoList(resultDtoList);

            allPartyDto.add(gameDto);
        });

        return allPartyDto;
    }

}
