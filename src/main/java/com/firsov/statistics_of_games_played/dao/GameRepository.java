package com.firsov.statistics_of_games_played.dao;

import com.firsov.statistics_of_games_played.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findByName(String name);

}
