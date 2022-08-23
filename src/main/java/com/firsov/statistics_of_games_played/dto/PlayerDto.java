package com.firsov.statistics_of_games_played.dto;

import javax.validation.constraints.Size;
import java.util.Objects;

public class PlayerDto {
    private int id;

    @Size(min = 2, max = 15, message = "введите от 2 до 15 символов")
    private String username;
    @Size(min = 3, max = 25, message = "введите от 3 до 25 символов")
    private String name;



    public PlayerDto() {
    }

    public PlayerDto(int id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDto playerDto = (PlayerDto) o;
        return id == playerDto.id && Objects.equals(username, playerDto.username) && Objects.equals(name, playerDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name);
    }
/*
    @Override
    public int compareTo(PlayerDto o) {
        return o.getScore() - this.getScore();
    }

    public int compareByCount(PlayerDto playerDto) {
        return Integer.compare(playerDto.getCount(), count);
    }

 */
}
