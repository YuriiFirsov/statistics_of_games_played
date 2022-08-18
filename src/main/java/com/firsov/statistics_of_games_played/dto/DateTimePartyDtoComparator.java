package com.firsov.statistics_of_games_played.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DateTimePartyDtoComparator implements Comparator<PartyDto>{
    @Override
    public int compare(PartyDto o1, PartyDto o2) {
        DateTimeFormatter df = DateTimeFormatter .ofPattern("yyyy.MM.dd HH:mm");
        return LocalDateTime.parse(o2.getPartyDate(), df).compareTo(LocalDateTime.parse(o1.getPartyDate(), df));
    }
}
