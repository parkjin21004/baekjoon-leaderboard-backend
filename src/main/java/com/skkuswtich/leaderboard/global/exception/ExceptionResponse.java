package com.skkuswtich.leaderboard.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public record ExceptionResponse(int code, String message) {
}
