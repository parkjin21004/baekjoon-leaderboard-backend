package com.skkuswtich.leaderboard.leaderboard.dto.solvedac;

import com.skkuswtich.leaderboard.leaderboard.domain.Rating;
import com.skkuswtich.leaderboard.leaderboard.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SolvedAcResponse {
    private String handle;
    private Long rating;
    private Long tier;

    public Rating toEntity(User user) {
        return new Rating(null, user, rating, tier, LocalDate.now());
    }
}
