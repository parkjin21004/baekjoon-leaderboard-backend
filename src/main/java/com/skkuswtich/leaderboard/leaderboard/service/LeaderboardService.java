package com.skkuswtich.leaderboard.leaderboard.service;

import com.skkuswtich.leaderboard.leaderboard.domain.Team;
import com.skkuswtich.leaderboard.leaderboard.domain.User;
import com.skkuswtich.leaderboard.leaderboard.dto.detail.DailyRating;
import com.skkuswtich.leaderboard.leaderboard.dto.detail.DetailResponse;
import com.skkuswtich.leaderboard.leaderboard.dto.solvedac.SolvedAcResponse;
import com.skkuswtich.leaderboard.leaderboard.dto.team.TeamInfo;
import com.skkuswtich.leaderboard.leaderboard.dto.detail.MemberDetailInfo;
import com.skkuswtich.leaderboard.leaderboard.dto.team.TeamMemberInfo;
import com.skkuswtich.leaderboard.leaderboard.repository.TeamRepository;
import com.skkuswtich.leaderboard.leaderboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final RatingService ratingService;
    private final UserService userService;
    private final SolvedAcService solvedAcService;

    @Transactional(readOnly = true)
    public List<TeamInfo> getAllTeam() {
        List<com.skkuswtich.leaderboard.leaderboard.domain.Team> teams = teamRepository.findAll();

        List<Pair<Team, Long>> teamsWithGain = teams.stream().map(team -> {
            List<User> users = userRepository.findAllByTeamId(team);
            long gain = users.stream()
                    .mapToLong(user -> user.getCurrentRating() - user.getInitialRating())
                    .sum();
            return new Pair<>(team, gain);
        })
                .sorted(Comparator.comparing((Pair<Team, Long> p) -> p.b).reversed())
                .toList();

        AtomicLong prevGain = new AtomicLong(Long.MIN_VALUE);
        AtomicLong prevOrder = new AtomicLong(1);

        return IntStream.range(0, teamsWithGain.size()).mapToObj(i -> {
            Pair<Team, Long> pair = teamsWithGain.get(i);
            Team team = pair.a;
            Long gain = pair.b;

            long order = prevOrder.get();
            if (!Objects.equals(prevGain.get(), gain)) {
                order = i+1;
                prevOrder.set(order);
            }
            prevGain.set(gain);

            List<User> users = userRepository.findAllByTeamId(team);

            List<TeamMemberInfo> members = users.stream()
                    .map(user -> new TeamMemberInfo(user.getName(), user.getBojId(), user.getCurrentTier()))
                    .toList();


            return new TeamInfo(team.getId(), team.getName(), gain, order, members);
        }).toList();
    }

    @Transactional(readOnly = true)
    public DetailResponse getTeamDetail(Long teamId) {
        com.skkuswtich.leaderboard.leaderboard.domain.Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not Found"));
        List<User> users = userRepository.findAllByTeamId(team);

        List<MemberDetailInfo> memberDetailInfos = users.stream()
                .map(user -> new MemberDetailInfo(
                        user.getName(),
                        user.getBojId(),
                        user.getCurrentRating()-user.getInitialRating(),
                        user.getCurrentRating(),
                        user.getCurrentTier(),
                        user.getEntranceYear()))
                .toList();

        List<DailyRating> dailyRatings = ratingService.getDailyRatingsByUsers(users);

        return new DetailResponse(teamId, memberDetailInfos, dailyRatings);
    }

    @Transactional
    public void recordAndUpdateUserRating() {
        List<User> users = userRepository.findAll();
        List<Pair<User, SolvedAcResponse>> solvedAcResponsesWithUser = users
                .stream()
                .map(user -> new Pair<>(user, solvedAcService.searchUser(user)))
                .toList();
        ratingService.createOrUpdateRating(solvedAcResponsesWithUser);
        userService.updateUser(solvedAcResponsesWithUser);
    }
}
