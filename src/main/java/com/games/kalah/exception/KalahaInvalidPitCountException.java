package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class KalahaInvalidPitCountException extends KalahaException {
    public KalahaInvalidPitCountException(ErrorCode code, String message) {
        super(code, message);
    }
}
