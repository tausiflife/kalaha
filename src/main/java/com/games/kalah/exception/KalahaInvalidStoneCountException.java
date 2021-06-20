package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class KalahaInvalidStoneCountException extends KalahaException {
    public KalahaInvalidStoneCountException(ErrorCode code, String message) {
        super(code, message);
    }
}
