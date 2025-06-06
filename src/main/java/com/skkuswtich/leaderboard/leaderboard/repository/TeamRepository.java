package com.skkuswtich.leaderboard.leaderboard.repository;

import com.skkuswtich.leaderboard.leaderboard.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
