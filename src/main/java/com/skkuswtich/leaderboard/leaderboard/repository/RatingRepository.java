package com.skkuswtich.leaderboard.leaderboard.repository;

import com.skkuswtich.leaderboard.leaderboard.domain.Rating;
import com.skkuswtich.leaderboard.leaderboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r FROM Rating r " +
           "WHERE (r.userId.id = :userId)")
    List<Rating> findAllDailyRatingsByUserId(@Param("userId") Long userId);
    Optional<Rating> findByUserIdAndCreatedAt(User user, LocalDate createdAt);
}
