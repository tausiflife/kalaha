package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class KalahaGameNotFoundException extends KalahaException {
    public KalahaGameNotFoundException(ErrorCode code, String message) {
        super(code, message);
    }
}
