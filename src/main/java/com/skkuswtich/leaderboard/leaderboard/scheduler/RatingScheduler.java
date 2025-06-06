package com.skkuswtich.leaderboard.leaderboard.scheduler;

import com.skkuswtich.leaderboard.leaderboard.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingScheduler {
    private final LeaderboardService leaderboardService;

    @Scheduled(cron = "0 0 * * * *")
    public void updateRatingHourly () {
        leaderboardService.recordAndUpdateUserRating();
    }
}
