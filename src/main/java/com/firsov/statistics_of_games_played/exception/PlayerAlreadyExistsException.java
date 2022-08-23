package com.firsov.statistics_of_games_played.exception;

public class PlayerAlreadyExistsException extends RuntimeException{

    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
