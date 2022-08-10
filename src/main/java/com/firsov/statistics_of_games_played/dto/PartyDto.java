package com.firsov.statistics_of_games_played.dto;

import java.util.List;

public class PartyDto {

    private int id;
    private String gameName;
    private  String partyDate;
    private List<ResultDto> resultDtoList;

    public PartyDto() {
    }

    public PartyDto(int id, String gameName, String partyDate, List<ResultDto> resultDtoList) {
        this.id = id;
        this.gameName = gameName;
        this.partyDate = partyDate;
        this.resultDtoList = resultDtoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(String partyDate) {
        this.partyDate = partyDate;
    }

    public List<ResultDto> getResultDtoList() {
        return resultDtoList;
    }

    public void setResultDtoList(List<ResultDto> resultDtoList) {
        this.resultDtoList = resultDtoList;
    }
}
