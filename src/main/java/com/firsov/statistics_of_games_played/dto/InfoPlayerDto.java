package com.firsov.statistics_of_games_played.dto;

public class InfoPlayerDto extends PlayerDto implements Comparable<InfoPlayerDto>{

    private int score;
    private int count;


    public InfoPlayerDto(int id, String username, String name) {
        super(id, username, name);
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public int compareTo(InfoPlayerDto o) {
        return o.getScore() - this.getScore();
    }

    public int compareByCount(InfoPlayerDto playerDto) {
        return Integer.compare(playerDto.getCount(), count);
    }
}
