package com.games.kalah.domain;

import com.games.kalah.domain.boards.BoardImpl;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardImplTest {

    private static final int INITIAL_PIT_STONES = 6;
    private static final int INITIAL_PIT_COUNT = 14;
    private BoardImpl boardImpl;

    @Before
    public void setUp(){
        KalahaGame game = new KalahaGame(INITIAL_PIT_COUNT, INITIAL_PIT_STONES);
        this.boardImpl = new BoardImpl(game);
    }

    @Test
    public void should_validate_is_pit_is_between_player1_pits() {
        assertTrue(boardImpl.isPitIdBetweenPlayer1Pits(1));
    }

    @Test
    public void should_validate_is_pit_is_not_between_player1_pits() {
        assertFalse(boardImpl.isPitIdBetweenPlayer1Pits(-1));
        assertFalse(boardImpl.isPitIdBetweenPlayer1Pits(8));
        assertFalse(boardImpl.isPitIdBetweenPlayer1Pits(14));
    }

    @Test
    public void should_validate_is_pit_is_between_player2_pits() {
        assertTrue(boardImpl.isPitIdBetweenPlayer2Pits(8));
    }

    @Test
    public void should_validate_is_pit_is_not_between_player2_pits() {
        assertFalse(boardImpl.isPitIdBetweenPlayer2Pits(1));
        assertFalse(boardImpl.isPitIdBetweenPlayer2Pits(-1));
        assertFalse(boardImpl.isPitIdBetweenPlayer2Pits(16));
    }

    @Test
    public void should_validate_if_pit_kalaha() {
        assertTrue(boardImpl.isThePitKalah(6));
        assertTrue(boardImpl.isThePitKalah(13));
    }

    @Test
    public void should_validate_if_pits_is_not_kalaha() {
        assertFalse(boardImpl.isThePitKalah(1));
        assertFalse(boardImpl.isThePitKalah(10));
    }

    @Test
    public void pit_should_be_valid_for_move_based_on_player() throws KalahaException {
        assertTrue(boardImpl.isValidPitIdForMove(10, Player.PLAYER_2));
        assertTrue(boardImpl.isValidPitIdForMove(5, Player.PLAYER_1));
    }

    @Test
    public void kalah_pit_should_be_invalid_for_move() throws KalahaException {
        KalahaException exception = assertThrows(
                KalahaInvalidPitIdException.class,
                () -> { boardImpl.isValidPitIdForMove(6, Player.PLAYER_1); }
        );
        assertEquals("Selected Pit is Kalah", exception.getMessage());
        assertEquals(ErrorCode.INVALID_PIT, exception.getErrorCode());
    }

    @Test
    public void pit_outside_range_should_be_invalid_for_move() throws KalahaException {
        KalahaException exception = assertThrows(
                KalahaInvalidPitIdException.class,
                () -> { boardImpl.isValidPitIdForMove(14, Player.PLAYER_1); }
        );
        assertEquals("Pit Id should be between range", exception.getMessage());
        assertEquals(ErrorCode.INVALID_PIT, exception.getErrorCode());
    }

    @Test
    public void should_return_player_1_for_newly_created_kalaha_game() {
        assertEquals(Player.PLAYER_1, boardImpl.getCurrentPlayer());
    }

    @Test
    public void should_get_opposite_pit() {
        assertEquals(12, boardImpl.calculateOppositePit(0));
        assertEquals(4, boardImpl.calculateOppositePit(8));
        assertEquals(4, boardImpl.calculateOppositePit(8));
    }
    @Test
    public void should_get_next_player() {
        assertEquals(Player.PLAYER_2, boardImpl.getNextPlayer(Player.PLAYER_1));
        assertEquals(Player.PLAYER_1, boardImpl.getNextPlayer(Player.PLAYER_2));
    }
    @Test
    public void should_update_player_turn() {
        boardImpl.updatePlayerTurn(1, Player.PLAYER_1);
        assertEquals(Player.PLAYER_2, boardImpl.getCurrentPlayer());
    }
    @Test
    public void should_not_update_player_turn_with_last_pit_as_kalaha() {
        boardImpl.updatePlayerTurn(6, Player.PLAYER_1);
        assertEquals(Player.PLAYER_1, boardImpl.getCurrentPlayer());
        boardImpl.updatePlayerTurn(13, Player.PLAYER_1);
        assertEquals(Player.PLAYER_2, boardImpl.getCurrentPlayer());
    }



    @Test
    public void should_sow_stones() throws KalahaException {
        boardImpl.sowStones(0);
        assertEquals(0, boardImpl.getGame().getPit(0).getStones());
        assertTrue(IntStream.rangeClosed(1, boardImpl.getGame().getPlayer1KalahaIndex() - 1).allMatch(p -> boardImpl.getGame().getPit(p).getStones() == INITIAL_PIT_STONES + 1));
        assertEquals(1, boardImpl.getGame().getPit(boardImpl.getGame().getPlayer1KalahaIndex()).getStones());
        assertEquals(Player.PLAYER_1, boardImpl.getCurrentPlayer());
        IntStream.rangeClosed(boardImpl.getGame().getPlayer2FirstPitIndex(), boardImpl.getGame().getPlayer2KalahaIndex() - 1).forEach(p -> System.out.println(boardImpl.getGame().getPit(p).getStones()));
        assertTrue(IntStream.rangeClosed(boardImpl.getGame().getPlayer2FirstPitIndex(), boardImpl.getGame().getPlayer2KalahaIndex() - 1).allMatch(p -> boardImpl.getGame().getPit(p).getStones() == INITIAL_PIT_STONES));
    }

    @Test
    public void should_sow_stones_ending_on_other_players_pit() throws KalahaException {
        boardImpl.sowStones(4);
        int[] noStonesChangedPits = new int[] {0,1,2,3, 11, 12};
        assertTrue(Arrays.stream(noStonesChangedPits).allMatch(p -> boardImpl.getGame().getPit(p).getStones() == INITIAL_PIT_STONES));
        assertEquals(1, boardImpl.getGame().getPit(boardImpl.getGame().getPlayer1KalahaIndex()).getStones());
        int[] pitsWithAddedStones = new int[] {5, 7, 8, 9, 10};
        assertTrue(Arrays.stream(pitsWithAddedStones).allMatch(p -> boardImpl.getGame().getPit(p).getStones() == INITIAL_PIT_STONES + 1));
        assertEquals(0, boardImpl.getGame().getPit(boardImpl.getGame().getPlayer2KalahaIndex()).getStones());
        assertEquals(0, boardImpl.getGame().getPit(4).getStones());
        assertEquals(Player.PLAYER_2, boardImpl.getCurrentPlayer());
    }

    @Test
    public void should_sow_stones_with_player_2() throws KalahaException {
        //Given player 1 moves from index 4 set current player as player 2
        boardImpl.sowStones(4);
        //player 2 moves from 9
        boardImpl.sowStones(9);

        int[] pitsWith7Stones = new int[] {11, 12, 0, 1, 2, 5, 7, 8};
        assertTrue(Arrays.stream(pitsWith7Stones).allMatch(p -> boardImpl.getGame().getPit(p).getStones() == INITIAL_PIT_STONES + 1));
        assertEquals(8, boardImpl.getGame().getPit(10).getStones());
        assertEquals(6, boardImpl.getGame().getPit(3).getStones());

        assertEquals(1, boardImpl.getGame().getPit(boardImpl.getGame().getPlayer2KalahaIndex()).getStones());
        assertEquals(1, boardImpl.getGame().getPit(boardImpl.getGame().getPlayer1KalahaIndex()).getStones());

        assertEquals(0, boardImpl.getGame().getPit(9).getStones());
        assertEquals(0, boardImpl.getGame().getPit(4).getStones());
        assertEquals(Player.PLAYER_1, boardImpl.getCurrentPlayer());
    }

}
