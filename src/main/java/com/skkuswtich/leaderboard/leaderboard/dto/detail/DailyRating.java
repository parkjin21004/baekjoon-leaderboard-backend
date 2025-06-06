package com.skkuswtich.leaderboard.leaderboard.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class DailyRating {
    private LocalDate date;
    private List<DailyRatingItem> ratings;

}
