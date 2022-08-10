package com.firsov.statistics_of_games_played.entity;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "party_to_the_game")
public class PartyToTheGame {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "date")
    private String date;


    public PartyToTheGame() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
