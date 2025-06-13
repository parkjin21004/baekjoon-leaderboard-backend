package com.skkuswtich.leaderboard.leaderboard.controller;

import com.skkuswtich.leaderboard.leaderboard.dto.detail.DetailResponse;
import com.skkuswtich.leaderboard.leaderboard.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/detail")
@RequiredArgsConstructor
public class DetailController {
    private final LeaderboardService leaderboardService;

    @GetMapping("/{id}")
    public ResponseEntity<DetailResponse> getUserDetails(@PathVariable("id") Long id) {
        DetailResponse userDetailResponses = leaderboardService.getTeamDetail(id);
        return ResponseEntity.ok(userDetailResponses);
    }
}
