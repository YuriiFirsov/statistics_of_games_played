package com.firsov.statistics_of_games_played.entity;

import javax.persistence.*;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "party_to_the_game_id", referencedColumnName = "id")
    private PartyToTheGame partyToTheGame;

    @ManyToOne()
    @JoinColumn(name = "players_id")
    private Player player;

    @Column(name = "number_of_points_per_game")
    private int numberOfPointsPerGame;

    public Result() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PartyToTheGame getPartyToTheGame() {
        return partyToTheGame;
    }

    public void setPartyToTheGame(PartyToTheGame partyToTheGame) {
        this.partyToTheGame = partyToTheGame;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getNumberOfPointsPerGame() {
        return numberOfPointsPerGame;
    }

    public void setNumberOfPointsPerGame(int numberOfPointsPerGame) {
        this.numberOfPointsPerGame = numberOfPointsPerGame;
    }

    @Override
    public String toString() {
        return "Result{ player=" + player +
                ", numberOfPointsPerGame=" + numberOfPointsPerGame +
                '}';
    }
}
