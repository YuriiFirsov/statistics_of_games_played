package com.firsov.statistics_of_games_played.exception;

/**
 * Исключение, выбрасываемое при попытке добавить игру уже записанную в БД
 */
public class GameAlreadyExistsException extends RuntimeException{

    public GameAlreadyExistsException(String message) {
        super(message);
    }

}
