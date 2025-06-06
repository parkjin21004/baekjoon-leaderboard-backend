package com.skkuswtich.leaderboard.leaderboard.repository;

import com.skkuswtich.leaderboard.leaderboard.domain.Team;
import com.skkuswtich.leaderboard.leaderboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByTeamId(Team teamId);
}
