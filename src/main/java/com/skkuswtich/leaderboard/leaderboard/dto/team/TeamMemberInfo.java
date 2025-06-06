package com.skkuswtich.leaderboard.leaderboard.dto.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamMemberInfo {
    private String name;
    private String bojId;
    private Long tier;
}
