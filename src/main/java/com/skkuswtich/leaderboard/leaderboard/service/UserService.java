package com.skkuswtich.leaderboard.leaderboard.service;

import com.skkuswtich.leaderboard.leaderboard.domain.User;
import com.skkuswtich.leaderboard.leaderboard.dto.solvedac.SolvedAcResponse;
import com.skkuswtich.leaderboard.leaderboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void updateUser(List<Pair<User, SolvedAcResponse>> solvedAcResponsesWithUser) {
        for (Pair<User, SolvedAcResponse> pair: solvedAcResponsesWithUser) {
            User user = userRepository.findById(pair.a.getId()).orElseThrow(() -> new RuntimeException("User not Found"));

            user.setCurrentRating(pair.b.getRating());
            user.setCurrentTier(pair.b.getTier());
        }
    }
}

