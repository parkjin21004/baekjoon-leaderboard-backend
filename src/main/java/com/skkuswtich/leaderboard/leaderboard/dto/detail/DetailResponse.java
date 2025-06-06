package com.skkuswtich.leaderboard.leaderboard.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DetailResponse {
    private Long id;
    private List<MemberDetailInfo> members;
    private List<DailyRating> dailyRatings;
}
