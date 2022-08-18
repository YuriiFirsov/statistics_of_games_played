package com.firsov.statistics_of_games_played.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime date;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
