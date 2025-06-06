package com.skkuswtich.leaderboard.leaderboard.service;

import com.skkuswtich.leaderboard.leaderboard.domain.Rating;
import com.skkuswtich.leaderboard.leaderboard.domain.User;
import com.skkuswtich.leaderboard.leaderboard.dto.detail.DailyRating;
import com.skkuswtich.leaderboard.leaderboard.dto.detail.DailyRatingItem;
import com.skkuswtich.leaderboard.leaderboard.dto.solvedac.SolvedAcResponse;
import com.skkuswtich.leaderboard.leaderboard.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    @Transactional
    public void createOrUpdateRating(List<Pair<User, SolvedAcResponse>> solvedAcResponsesWithUser) {
        LocalDate today = LocalDate.now();

        for (Pair<User, SolvedAcResponse> pair : solvedAcResponsesWithUser) {
            User user = pair.a;
            SolvedAcResponse solvedAcResponse = pair.b;

            Rating rating = ratingRepository.findByUserIdAndCreatedAt(user, today)
                    .orElseGet(() -> solvedAcResponse.toEntity(user));

            rating.setRating(solvedAcResponse.getRating());
            rating.setTier(solvedAcResponse.getTier());

            ratingRepository.save(rating);
        }
    }

    @Transactional(readOnly = true)
    public List<DailyRating> getDailyRatingsByUsers (List<User> users) {

        List<Rating> ratings = users.stream()
                .flatMap(user ->
                        ratingRepository.findAllDailyRatingsByUserId(user.getId()).stream())
                .toList();

        return ratings.stream()
                .collect(Collectors.groupingBy(Rating::getCreatedAt))
                .entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<DailyRatingItem> dailyRatingItems = entry.getValue().stream()
                            .map(r -> new DailyRatingItem(r.getUserId().getName(), r.getRating()))
                            .toList();

                    return new DailyRating(date, dailyRatingItems);
                })
                .sorted(Comparator.comparing(DailyRating::getDate))
                .toList();
    }
}
