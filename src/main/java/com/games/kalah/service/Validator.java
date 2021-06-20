package com.games.kalah.service;

import com.games.kalah.constants.KalahConstants;
import com.games.kalah.domain.ErrorCode;
import com.games.kalah.exception.KalahaInvalidStoneCountException;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Value("${kalaha.exception.stones.invalid.msg}")
    private String invalidStoneCountMessage;


    @Value("${kalaha.exception.pitid.invalid.msg}")
    private String invalidPitIdMessage;

    public boolean isValidStonesCount(int stonesCount) throws KalahaException {
        if (stonesCount < 1)
            throw new KalahaInvalidStoneCountException(ErrorCode.INVALID_STONES, invalidStoneCountMessage);
        return true;
    }



}
