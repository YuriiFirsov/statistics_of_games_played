package com.firsov.statistics_of_games_played.exception;

/**
 * Исключение, выбрасываемое при попытке добавить игрока с занятым username
 */
public class PlayerAlreadyExistsException extends RuntimeException{

    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
