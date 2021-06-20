package com.games.kalah.handler;

import com.games.kalah.dto.KalahExceptionResponse;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidStoneCountException;
import com.games.kalah.exception.KalahaGameNotFoundException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(value = KalahaGameNotFoundException.class)
    public ResponseEntity<KalahExceptionResponse> handleKalahGameNotFoundException(KalahaException exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(exception.getErrorCode().getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = KalahaInvalidPitIdException.class)
    public ResponseEntity<KalahExceptionResponse> handleInValidPitIdException(KalahaException exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(exception.getErrorCode().getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = KalahaInvalidStoneCountException.class)
    public ResponseEntity<String> handleInvalidStonesException(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
