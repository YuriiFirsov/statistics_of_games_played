package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.GameRepository;
import com.firsov.statistics_of_games_played.dao.PartyToTheGameRepository;
import com.firsov.statistics_of_games_played.dao.PlayerRepository;
import com.firsov.statistics_of_games_played.dao.ResultRepository;
import com.firsov.statistics_of_games_played.dto.DateTimePartyDtoComparator;
import com.firsov.statistics_of_games_played.dto.InfoPartyDto;
import com.firsov.statistics_of_games_played.dto.PartyDto;
import com.firsov.statistics_of_games_played.dto.ResultDto;
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

    private final PartyToTheGameRepository partyToTheGameRepository;
    private final ResultRepository resultRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Autowired
    public PartyToTheGameService(PartyToTheGameRepository partyToTheGameRepository,
                                 ResultRepository resultRepository,
                                 PlayerRepository playerRepository,
                                 GameRepository gameRepository) {
        this.partyToTheGameRepository = partyToTheGameRepository;
        this.resultRepository = resultRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public List<PartyDto> getAllPartiesDto() {

        List<Result> results = resultRepository.findAll();

        return getListPartyDto(results);
    }

    public List<PartyDto> getAllPartiesDtoSelectedGame(String selectedGame) {

        List<Result> results = resultRepository.findAllByPartyToTheGame_Game_Name(selectedGame);

        return getListPartyDto(results);
    }

    public void saveOrUpdateParty(InfoPartyDto infoPartyDto) {

        PartyToTheGame partyToTheGame = new PartyToTheGame();
        partyToTheGame.setId(infoPartyDto.getIdParty());
        partyToTheGame.setGame(gameRepository.findByName(infoPartyDto.getGameName()));

        if (!Objects.equals(infoPartyDto.getDate(), "")) {
            partyToTheGame.setDate(LocalDateTime.parse(infoPartyDto.getDate()));
        } else {
            partyToTheGame.setDate(LocalDateTime.now());
        }

        partyToTheGameRepository.save(partyToTheGame);

        recordANewResult(partyToTheGame.getId(), infoPartyDto.getIdResult1(), infoPartyDto.getUsernamePlayer1(), infoPartyDto.getScorePlayer1());
        recordANewResult(partyToTheGame.getId(), infoPartyDto.getIdResult2(), infoPartyDto.getUsernamePlayer2(), infoPartyDto.getScorePlayer2());
        recordANewResult(partyToTheGame.getId(), infoPartyDto.getIdResult3(), infoPartyDto.getUsernamePlayer3(), infoPartyDto.getScorePlayer3());
        recordANewResult(partyToTheGame.getId(), infoPartyDto.getIdResult4(), infoPartyDto.getUsernamePlayer4(), infoPartyDto.getScorePlayer4());

    }

    public void deletePartyToTheGame(int id) {

        resultRepository.deleteAllByPartyToTheGame_Id(id);
        partyToTheGameRepository.deleteById(id);
    }

    public void recordANewResult(int idParty, int idResult, String player, int score) {
        if (!player.equals("Выберите имя")) {
            Result result = new Result();
            result.setId(idResult);
            result.setPartyToTheGame(partyToTheGameRepository.findById(idParty));
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

        listOfResultsGroupedByParty.forEach(currentResults -> {
            PartyDto partyDto = new PartyDto();

            PartyToTheGame partyToTheGame = currentResults.get(0).getPartyToTheGame();

            partyDto.setId(partyToTheGame.getId());
            partyDto.setGameName(partyToTheGame.getGame().getName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            partyDto.setPartyDate(partyToTheGame.getDate().format(formatter));

            List<ResultDto> resultDtoList = currentResults
                    .stream()
                    .map(result ->
                            new ResultDto(
                                    result.getPlayer().getId(),
                                    result.getPlayer().getUsername(),
                                    result.getNumberOfPointsPerGame()
                            )).toList();
            partyDto.setResultDtoList(resultDtoList);

            allPartyDto.add(partyDto);
        });

        return allPartyDto.stream().sorted(new DateTimePartyDtoComparator()).collect(Collectors.toList());
    }

    public InfoPartyDto collectionNewPartyDto(int id) {
        InfoPartyDto infoPartyDto = new InfoPartyDto();
        PartyToTheGame partyToTheGame = partyToTheGameRepository.findById(id);
        List<Result> results = resultRepository.findAllByPartyToTheGame_Id(id);

        infoPartyDto.setIdParty(partyToTheGame.getId());
        infoPartyDto.setGameName(partyToTheGame.getGame().getName());
        infoPartyDto.setDate(partyToTheGame.getDate().toString());

        if (results.size() > 0) {
            infoPartyDto.setIdResult1(results.get(0).getId());
            infoPartyDto.setUsernamePlayer1(results.get(0).getPlayer().getUsername());
            infoPartyDto.setScorePlayer1(results.get(0).getNumberOfPointsPerGame());
        }

        if (results.size() > 1) {
        infoPartyDto.setIdResult2(results.get(1).getId());
        infoPartyDto.setUsernamePlayer2(results.get(1).getPlayer().getUsername());
        infoPartyDto.setScorePlayer2(results.get(1).getNumberOfPointsPerGame());
        }

        if (results.size() > 2) {
            infoPartyDto.setIdResult3(results.get(2).getId());
            infoPartyDto.setUsernamePlayer3(results.get(2).getPlayer().getUsername());
            infoPartyDto.setScorePlayer3(results.get(2).getNumberOfPointsPerGame());
        }

        if (results.size() > 3) {
            infoPartyDto.setIdResult4(results.get(3).getId());
            infoPartyDto.setUsernamePlayer4(results.get(3).getPlayer().getUsername());
            infoPartyDto.setScorePlayer4(results.get(3).getNumberOfPointsPerGame());
        }

        return infoPartyDto;
    }
}
