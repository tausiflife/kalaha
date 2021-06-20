package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class InvalidPlayerException extends KalahaException {
    InvalidPlayerException(ErrorCode code, String message) {
        super(code, message);
    }
}
