package com.skkuswtich.leaderboard.leaderboard.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetailInfo {
    private String name;
    private String bojId;
    private Long gain;
    private Long rating;
    private Long tier;
    private Long entranceYear;
}
