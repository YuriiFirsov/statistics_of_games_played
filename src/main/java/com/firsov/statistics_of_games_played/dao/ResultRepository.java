package com.firsov.statistics_of_games_played.dao;


import com.firsov.statistics_of_games_played.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ResultRepository extends JpaRepository<Result, Integer> {

     List<Result> findAllByPartyToTheGame_Game_Name(String selectedGame);

     List<Result>findAllByPartyToTheGame_Id(int id);

     void deleteAllByPartyToTheGame_Id (int id);

}
