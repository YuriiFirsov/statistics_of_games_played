package com.firsov.statistics_of_games_played.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class GameDto {
    private int id;

    @NotBlank(message = "название игры не должно быть пустым")
    @Size(max = 25, message = "название игры не должно превышать 25 символов")
    private String name;

    public GameDto() {
    }

    public GameDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
