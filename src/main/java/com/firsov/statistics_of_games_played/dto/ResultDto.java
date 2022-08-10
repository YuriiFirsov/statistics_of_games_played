package com.firsov.statistics_of_games_played.dto;

public class ResultDto {

    private int playerId;
    private String playerName;
    private  int playerScore;


    public ResultDto(int playerId, String playerName, int playerScore) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
