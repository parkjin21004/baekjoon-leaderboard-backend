package com.skkuswtich.leaderboard.leaderboard.controller;

import com.skkuswtich.leaderboard.leaderboard.dto.team.TeamInfo;
import com.skkuswtich.leaderboard.leaderboard.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<TeamInfo>> getTeams() {
        List<TeamInfo> teamResponses = leaderboardService.getAllTeam();
        return ResponseEntity.ok(teamResponses);
    }
}
