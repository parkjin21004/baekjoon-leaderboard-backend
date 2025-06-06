package com.skkuswtich.leaderboard.leaderboard.dto.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamInfo {
    private Long teamId;
    private String teamName;
    private Long gain;
    private Long order;
    private List<TeamMemberInfo> members;
}
