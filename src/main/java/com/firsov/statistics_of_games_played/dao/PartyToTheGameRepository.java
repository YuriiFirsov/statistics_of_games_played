package com.firsov.statistics_of_games_played.dao;

import com.firsov.statistics_of_games_played.entity.PartyToTheGame;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartyToTheGameRepository extends JpaRepository<PartyToTheGame, Integer> {

    PartyToTheGame findById(int id);

}
