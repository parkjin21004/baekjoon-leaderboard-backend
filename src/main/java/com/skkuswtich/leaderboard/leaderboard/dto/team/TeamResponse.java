package com.skkuswtich.leaderboard.leaderboard.dto.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamResponse {
    private List<TeamInfo> team;
}
