package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class KalahaInvalidPlayerException extends KalahaException {
    public KalahaInvalidPlayerException(ErrorCode code, String message) {
        super(code, message);
    }
}
