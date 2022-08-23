package com.firsov.statistics_of_games_played.dto;

import java.util.List;

public class InfoPartyDto {

    private int idParty;
    private String gameName;
    private String date;

    List<InfoPartyResultDto> infoPartyResults;

    public InfoPartyDto() {
    }

    public int getIdParty() {
        return idParty;
    }

    public void setIdParty(int idParty) {
        this.idParty = idParty;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<InfoPartyResultDto> getInfoPartyResults() {
        return infoPartyResults;
    }

    public void setInfoPartyResults(List<InfoPartyResultDto> infoPartyResults) {
        this.infoPartyResults = infoPartyResults;
    }
}
