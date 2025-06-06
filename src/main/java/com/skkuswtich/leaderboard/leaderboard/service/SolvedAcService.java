package com.skkuswtich.leaderboard.leaderboard.service;

import com.skkuswtich.leaderboard.leaderboard.domain.User;
import com.skkuswtich.leaderboard.leaderboard.dto.solvedac.SolvedAcResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SolvedAcService {
    private final WebClient webClient;

    public SolvedAcService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://solved.ac").build();
    }

    public SolvedAcResponse searchUser(User user) {
        SolvedAcResponse solvedAcResponse = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v3/user/show")
                .queryParam("handle", user.getBojId())
                .build()
            )
            .header("Accept", "application/json")
            .header("x-solvedac-language", "ko")
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, res -> Mono.error(new RuntimeException("사용자 없음: " + user.getName())))
            .onStatus(HttpStatusCode::is5xxServerError, res -> Mono.error(new RuntimeException("서버 오류")))
            .bodyToMono(SolvedAcResponse.class)
            .block();

        if (solvedAcResponse == null) {
            throw new RuntimeException("user [" + user.getName() + "] not found on solved.ac");
        }

        return solvedAcResponse;
    }
}
