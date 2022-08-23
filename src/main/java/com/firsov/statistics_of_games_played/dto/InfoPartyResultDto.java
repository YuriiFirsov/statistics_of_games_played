package com.firsov.statistics_of_games_played.dto;

public class InfoPartyResultDto {

    private int idResult;
    private String usernamePlayer;
    private int scorePlayer;

    public InfoPartyResultDto(int idResult, String usernamePlayer, int scorePlayer) {
        this.idResult = idResult;
        this.usernamePlayer = usernamePlayer;
        this.scorePlayer = scorePlayer;
    }

    public InfoPartyResultDto() {
    }

    public int getIdResult() {
        return idResult;
    }

    public void setIdResult(int idResult) {
        this.idResult = idResult;
    }

    public String getUsernamePlayer() {
        return usernamePlayer;
    }

    public void setUsernamePlayer(String usernamePlayer) {
        this.usernamePlayer = usernamePlayer;
    }

    public int getScorePlayer() {
        return scorePlayer;
    }

    public void setScorePlayer(int scorePlayer) {
        this.scorePlayer = scorePlayer;
    }
}
