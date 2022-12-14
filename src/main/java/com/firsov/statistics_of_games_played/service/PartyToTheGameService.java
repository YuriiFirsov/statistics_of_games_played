package com.firsov.statistics_of_games_played.service;

import com.firsov.statistics_of_games_played.dao.GameRepository;
import com.firsov.statistics_of_games_played.dao.PartyToTheGameRepository;
import com.firsov.statistics_of_games_played.dao.PlayerRepository;
import com.firsov.statistics_of_games_played.dao.ResultRepository;
import com.firsov.statistics_of_games_played.dto.*;
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
public class PartyToTheGameService {

    private static final int MAX_PLAYERS_COUNT = 4;

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

    /**
     * метод возвращает список сыгранных партий для отображения на главной странице
     */
    @Transactional
    public List<PartyDto> getAllPartiesDto() {

        return getListPartyDto(resultRepository.findAll());
    }

    /**
     * метод возвращает список сыгранных партий для отображения на странице результатов в конкретной игре
     */
    @Transactional
    public List<PartyDto> getAllPartiesDtoSelectedGame(String selectedGame) {

        return getListPartyDto(resultRepository.findAllByPartyToTheGame_Game_Name(selectedGame));
    }

    /**
     * метод преобразует partyDto в partyToTheGame для записи в БД,
     * а также, через метод recordANewResult записывает список result в БД
     */
    @Transactional
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

        infoPartyDto.getInfoPartyResults()
                .forEach(infoPartyResultDto -> recordANewResult(partyToTheGame.getId(), infoPartyResultDto));

    }

    @Transactional
    public void deletePartyToTheGame(int id) {

        resultRepository.deleteAllByPartyToTheGame_Id(id);
        partyToTheGameRepository.deleteById(id);
    }

    /**
     * метод преобразует из infoPartyResultDto в result для записи в БД
     */
    @Transactional
    public void recordANewResult(int idParty, InfoPartyResultDto infoPartyResultDto) {
        if (!infoPartyResultDto.getUsernamePlayer().equals("Выберите имя")) {
            Result result = new Result();
            result.setId(infoPartyResultDto.getIdResult());
            result.setPartyToTheGame(partyToTheGameRepository.findById(idParty));
            result.setPlayer(playerRepository.findByUsername(infoPartyResultDto.getUsernamePlayer()));
            result.setNumberOfPointsPerGame(infoPartyResultDto.getScorePlayer());
            resultRepository.save(result);
        }
    }

    /**
     * метод преобразует выбранный по id partyToTheGame и список results в infoPartyDto.
     * Поле infoPartyResults является списком с четко заданой размерностью, равной MAX_PLAYERS_COUNT,
     * если количество записей в БД, соответствующих заданому id меньше MAX_PLAYERS_COUNT,
     * разница заполняется пустыми объектами класса InfoPartyResultDto.
     */
    @Transactional
    public InfoPartyDto collectionInfoPartyDto(int id) {
        InfoPartyDto infoPartyDto = new InfoPartyDto();
        PartyToTheGame partyToTheGame = partyToTheGameRepository.findById(id);
        List<Result> results = resultRepository.findAllByPartyToTheGame_Id(id);

        infoPartyDto.setIdParty(partyToTheGame.getId());
        infoPartyDto.setGameName(partyToTheGame.getGame().getName());
        infoPartyDto.setDate(partyToTheGame.getDate().toString());

        List<InfoPartyResultDto> infoPartyResultDtos = results
                .stream()
                .map(result -> new InfoPartyResultDto(
                        result.getId(),
                        result.getPlayer().getUsername(),
                        result.getNumberOfPointsPerGame()
                ))
                .collect(Collectors.toList());

        while (infoPartyResultDtos.size() < MAX_PLAYERS_COUNT) {
            infoPartyResultDtos.add(new InfoPartyResultDto());
        }

        infoPartyDto.setInfoPartyResults(infoPartyResultDtos);

        return infoPartyDto;
    }

    /**
     * метод создает пустой объект класса InfoPartyDto для передачи в party_info.jsp
     * для заполнения его полей в форме
     */
    public InfoPartyDto creatingAnEmptyInfoPartyDto() {
        InfoPartyDto infoPartyDto = new InfoPartyDto();
        List<InfoPartyResultDto> infoPartyResultDtoList = new ArrayList<>();

        for (int i = 0; i < MAX_PLAYERS_COUNT; i++) {
            infoPartyResultDtoList.add(new InfoPartyResultDto());
        }

        infoPartyDto.setInfoPartyResults(infoPartyResultDtoList);

        return infoPartyDto;
    }

    /**
     * метод преобразует лист объектов класса Result в отсортированный по дате лист объектов класса PartyDto.
     * Сначало получаем лист листов объектов класса Result, сгруппированных по партиям.
     * Далее, проходя циклом по полученному листу листов из каждого вложенного листа формируется объект класса PartyDto.
     */
    private List<PartyDto> getListPartyDto(List<Result> results) {
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

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return allPartyDto
                .stream()
                .sorted((o1, o2) -> LocalDateTime.parse(o2.getPartyDate(),
                        df).compareTo(LocalDateTime.parse(o1.getPartyDate(), df)))
                .collect(Collectors.toList());
    }

}
