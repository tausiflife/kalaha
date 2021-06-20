package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class KalahaException extends Exception {
    private ErrorCode errorCode;
    KalahaException(ErrorCode code, String message) {
        super(message);
        this.errorCode = code;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
