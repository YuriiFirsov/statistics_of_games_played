package com.firsov.statistics_of_games_played.exception;

public class GameAlreadyExistsException extends RuntimeException{

    public GameAlreadyExistsException(String message) {
        super(message);
    }

}
