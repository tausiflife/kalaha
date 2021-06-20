package com.games.kalah.service;

import com.games.kalah.domain.ErrorCode;
import com.games.kalah.exception.KalahaInvalidStoneCountException;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorTest {

    @Autowired
    private Validator validator;

    @Value("${kalaha.exception.stones.invalid.msg}")
    public String invalidStoneCountMessage;

    @Value("${kalaha.exception.pitid.invalid.msg}")
    private String invalidPitIdMessage;

    @Test
    public void should_return_true_for_stones_greater_than_one() {
        try {
            boolean response = validator.isValidStonesCount(4);
            assertTrue(response);
        } catch (KalahaException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_throw_invalid_count_exception_with_stone_less_than_one() {
        KalahaException exception = assertThrows(
                KalahaInvalidStoneCountException.class,
                () -> { validator.isValidStonesCount(0); }
        );
        assertEquals(invalidStoneCountMessage, exception.getMessage());
        assertEquals(ErrorCode.INVALID_STONES, exception.getErrorCode());
    }

    @Test
    public void should_return_true_for_pitId_between_zero_and_thirteen() {
        /*try {
            boolean response = validator.isValidPitId(4);
            assertTrue(response);
        } catch (KalahaException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void should_throw_invalid_pit_id() {
        /*KalahaException exception = assertThrows(
                KalahaInvalidPitIdException.class,
                () -> { validator.isValidPitId(0); }
        );
        assertEquals(invalidPitIdMessage, exception.getMessage());
        assertEquals(ErrorCode.INVALID_PIT, exception.getErrorCode());*/
    }
}
