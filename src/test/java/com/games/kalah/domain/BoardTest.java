package com.games.kalah.domain;

import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardTest {

    private Board board;
    @Before
    public void setUp(){
        KalahaGame game = new KalahaGame(14, 6);
        this.board = new Board(game, 0);
    }

    @Test
    public void should_validate_is_pit_is_between_player1_pits() {
        assertTrue(board.isPitIdBetweenPlayer1Pits(1));
    }

    @Test
    public void should_validate_is_pit_is_not_between_player1_pits() {
        assertFalse(board.isPitIdBetweenPlayer1Pits(-1));
        assertFalse(board.isPitIdBetweenPlayer1Pits(8));
        assertFalse(board.isPitIdBetweenPlayer1Pits(14));
    }

    @Test
    public void should_validate_is_pit_is_between_player2_pits() {
        assertTrue(board.isPitIdBetweenPlayer2Pits(8));
    }

    @Test
    public void should_validate_is_pit_is_not_between_player2_pits() {
        assertFalse(board.isPitIdBetweenPlayer2Pits(1));
        assertFalse(board.isPitIdBetweenPlayer2Pits(-1));
        assertFalse(board.isPitIdBetweenPlayer2Pits(16));
    }

    @Test
    public void should_validate_if_pit_kalaha() {
        assertTrue(board.isThePitKalah(6));
        assertTrue(board.isThePitKalah(13));
    }

    @Test
    public void should_validate_if_pits_is_not_kalaha() {
        assertFalse(board.isThePitKalah(1));
        assertFalse(board.isThePitKalah(10));
    }

    @Test
    public void pit_should_be_valid_for_move() throws KalahaException {
        assertTrue(board.isValidPitIdForMove(5));
        assertTrue(board.isValidPitIdForMove(10));
    }

    @Test
    public void kalah_pit_should_be_invalid_for_move() throws KalahaException {
        KalahaException exception = assertThrows(
                KalahaInvalidPitIdException.class,
                () -> { board.isValidPitIdForMove(6); }
        );
        assertEquals("Selected Pit is Kalah", exception.getMessage());
        assertEquals(ErrorCode.INVALID_PIT, exception.getErrorCode());
    }

    @Test
    public void pit_outside_range_should_be_invalid_for_move() throws KalahaException {
        KalahaException exception = assertThrows(
                KalahaInvalidPitIdException.class,
                () -> { board.isValidPitIdForMove(14); }
        );
        assertEquals("Pit Id should be between 1-6 and 8-13", exception.getMessage());
        assertEquals(ErrorCode.INVALID_PIT, exception.getErrorCode());
    }
}
