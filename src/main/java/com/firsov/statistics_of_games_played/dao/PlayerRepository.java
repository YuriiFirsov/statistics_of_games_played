package com.firsov.statistics_of_games_played.dao;

import com.firsov.statistics_of_games_played.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByUsername(String username);
}
