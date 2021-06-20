package com.games.kalah.domain;

import lombok.Getter;

@Getter
public enum ErrorCode {
    GAME_NOT_FOUND(101),
    INVALID_PIT(102),
    INVALID_STONES(103),
    INVALID_STONES_IN_PIT(104),
    GAME_OVER(105),
    INVALID_PIT_COUNT(105);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
