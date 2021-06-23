package com.games.kalah.handler;

import com.games.kalah.dto.KalahExceptionResponse;
import com.games.kalah.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(value = KalahaGameNotFoundException.class)
    public ResponseEntity<KalahExceptionResponse> handleKalahGameNotFoundException(KalahaGameNotFoundException exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(exception.getErrorCode().getCode(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = KalahaInvalidPitIdException.class)
    public ResponseEntity<KalahExceptionResponse> handleInValidPitIdException(KalahaInvalidPitIdException exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(exception.getErrorCode().getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = KalahaInvalidStoneCountException.class)
    public ResponseEntity<KalahExceptionResponse> handleInvalidStonesException(KalahaInvalidStoneCountException exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(exception.getErrorCode().getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = KalahGameOverException.class)
    public ResponseEntity<KalahExceptionResponse> handleGameOverException(KalahGameOverException exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(exception.getErrorCode().getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = KalahaInvalidPlayerException.class)
    public ResponseEntity<KalahExceptionResponse> handleInvalidPlayerException(KalahaInvalidPlayerException exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(exception.getErrorCode().getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<KalahExceptionResponse> handleSomethingBadHappened(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(new KalahExceptionResponse(0, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
