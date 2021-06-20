package com.games.kalah.exception;

import com.games.kalah.domain.ErrorCode;

public class KalahGameOverException extends KalahaException{
    public KalahGameOverException(ErrorCode code, String message) {
        super(code, message);
    }
}
