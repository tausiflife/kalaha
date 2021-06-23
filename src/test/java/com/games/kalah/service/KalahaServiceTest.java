package com.games.kalah.service;

import com.games.kalah.domain.*;
import com.games.kalah.domain.boards.Board;
import com.games.kalah.domain.boards.BoardImpl;
import com.games.kalah.domain.boards.BoardFactory;
import com.games.kalah.exception.KalahGameOverException;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaGameNotFoundException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import com.games.kalah.repository.KalahaGameRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class KalahaServiceTest {

    @InjectMocks
    private KalahaService kalahaService = new KalahaService();

    @Mock
    private KalahaGameRepository kalahaGameRepository;

    @Mock
    private Validator validator;

    @Mock
    private BoardFactory boardFactory;

    @Test
    public void should_create_a_new_kalah_game() throws KalahaException {
        //given
        KalahaGame game = Mockito.mock(KalahaGame.class);
        when(game.getId()).thenReturn(10l);
        when(validator.isValidStonesCount(any(Integer.class))).thenReturn(true);
        when(kalahaGameRepository.save(any(KalahaGame.class))).thenReturn(game);

        //when
        long id = kalahaService.createGame(14, 6);

        //then
        assertNotNull(id);
        verify(validator, times(1)).isValidStonesCount(any(Integer.class));
        verify(kalahaGameRepository, times(1)).save(any(KalahaGame.class));
    }

    @Test
    public void should_throw_exception_with_invaild_game_id() throws KalahaException {
        //given
        when(kalahaGameRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        KalahaException exception = assertThrows(
                KalahaGameNotFoundException.class,
                () -> { kalahaService.makeMove(1, 5); }
        );
        assertTrue(CoreMatchers.containsString("Game not found with game id").matches(exception.getMessage()));
        assertEquals(ErrorCode.GAME_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    public void should_throw_exception_when_game_is_over() throws KalahaException {
        //given
        KalahaGame game = Mockito.mock(KalahaGame.class);
        when(game.isGameOver()).thenReturn(true);
        when(kalahaGameRepository.findById(any(Long.class))).thenReturn(Optional.of(game));

        KalahaException exception = assertThrows(
                KalahGameOverException.class,
                () -> { kalahaService.makeMove(1, 5); }
        );
        assertEquals("Game is already over", exception.getMessage());
        assertEquals(ErrorCode.GAME_OVER, exception.getErrorCode());

    }

    @Test
    public void should_throw_exception_when_selected_pit_has_no_stones() throws KalahaException {
        //given
        KalahaGame game = Mockito.mock(KalahaGame.class);
        KalahaPit pit = Mockito.mock(KalahaPit.class);
        when(game.getPit(any(Integer.class))).thenReturn(pit);
        when(pit.getStones()).thenReturn(0);
        when(kalahaGameRepository.findById(any(Long.class))).thenReturn(Optional.of(game));

        KalahaException exception = assertThrows(
                KalahaInvalidPitIdException.class,
                () -> { kalahaService.makeMove(1, 5); }
        );
        assertEquals("Selected pit is empty", exception.getMessage());
        assertEquals(ErrorCode.INVALID_PIT, exception.getErrorCode());

    }

    @Test
    public void should_make_move() throws KalahaException {
        //given
        KalahaGame game = Mockito.mock(KalahaGame.class);

        BoardImpl board = Mockito.mock(BoardImpl.class);
        when(boardFactory.createBoard(any(KalahaGame.class))).thenReturn(board);

        when(game.getPlayerWithNoStones()).thenReturn(Optional.ofNullable(null));
        when(game.getNoOfPits()).thenReturn(14);
        KalahaPit pit = Mockito.mock(KalahaPit.class);
        when(game.getPit(any(Integer.class))).thenReturn(pit);
        when(pit.getStones()).thenReturn(6);
        when(kalahaGameRepository.findById(any(Long.class))).thenReturn(Optional.of(game));


        //when
        List<KalahaPit> pits = kalahaService.makeMove(14, 6);

        //then
        assertNotNull(pits);
        verify(kalahaGameRepository, times(1)).findById(any(Long.class));
        verify(kalahaGameRepository, times(1)).save(any(KalahaGame.class));
        verify(boardFactory, times(1)).createBoard(any(KalahaGame.class));

    }

    @Test
    public void should_make_move_when_one_player_has_no_stones_left() throws KalahaException {
        //given
        BoardImpl boardImpl = Mockito.mock(BoardImpl.class);
        KalahaGame game = Mockito.mock(KalahaGame.class);
        when(game.getPlayerWithNoStones()).thenReturn(Optional.ofNullable(Player.PLAYER_1));
        KalahaPit pit = Mockito.mock(KalahaPit.class);
        when(game.getPit(any(Integer.class))).thenReturn(pit);
        when(pit.getStones()).thenReturn(6);
        when(kalahaGameRepository.findById(any(Long.class))).thenReturn(Optional.of(game));
        when(boardFactory.createBoard(any(KalahaGame.class))).thenReturn(boardImpl);

        //when
        List<KalahaPit> pits = kalahaService.makeMove(14, 6);

        //then
        assertNotNull(pits);
        verify(kalahaGameRepository, times(1)).findById(any(Long.class));
        verify(kalahaGameRepository, times(1)).save(any(KalahaGame.class));
        verify(boardFactory, times(1)).createBoard(any(KalahaGame.class));

    }
}
