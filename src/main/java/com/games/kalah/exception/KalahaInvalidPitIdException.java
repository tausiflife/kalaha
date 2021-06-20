package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class KalahaInvalidPitIdException extends KalahaException {
    public KalahaInvalidPitIdException(ErrorCode code, String message) {
        super(code, message);
    }
}
