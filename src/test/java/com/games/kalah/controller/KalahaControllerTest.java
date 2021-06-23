package com.games.kalah.controller;

import com.games.kalah.constants.KalahConstants;
import com.games.kalah.domain.ErrorCode;
import com.games.kalah.domain.KalahaPit;
import com.games.kalah.exception.KalahGameOverException;
import com.games.kalah.exception.KalahaGameNotFoundException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import com.games.kalah.exception.KalahaInvalidStoneCountException;
import com.games.kalah.service.KalahaService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = KalahaController.class)
public class KalahaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KalahaService kalahaService;

    @Test
    public void should_create_a_new_game() throws Exception {
        when(kalahaService.createGame(any(Integer.class), any(Integer.class))).thenReturn(10l);
        this.mockMvc.perform(post(KalahConstants.GAME_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.is("10")));

        verify(kalahaService, times(1)).createGame(any(Integer.class), any(Integer.class));
    }

    @Test
    public void should_make_move() throws Exception {
        List<KalahaPit> kalahaPits = new ArrayList<>();
        kalahaPits.add(new KalahaPit(0, 7));
        kalahaPits.add(new KalahaPit(1, 5));
        kalahaPits.add(new KalahaPit(2, 7));
        kalahaPits.add(new KalahaPit(3, 6));
        kalahaPits.add(new KalahaPit(4, 0));
        kalahaPits.add(new KalahaPit(5, 7));
        kalahaPits.add(new KalahaPit(6, 1));
        kalahaPits.add(new KalahaPit(7, 7));
        kalahaPits.add(new KalahaPit(8, 7));
        kalahaPits.add(new KalahaPit(9, 0));
        kalahaPits.add(new KalahaPit(10, 8));
        kalahaPits.add(new KalahaPit(11, 7));
        kalahaPits.add(new KalahaPit(12, 7));
        kalahaPits.add(new KalahaPit(13, 1));

        when(kalahaService.makeMove(any(Integer.class), any(Integer.class))).thenReturn(kalahaPits);
        this.mockMvc.perform(put(KalahConstants.GAME_ENDPOINT + "/{gameId}/pits/{pitId}", 1, 9)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.is("1")))
                .andExpect(jsonPath("$.status", notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.aMapWithSize(14)));

        verify(kalahaService, times(1)).makeMove(any(Integer.class), any(Integer.class));
    }

    @Test
    public void should_check_game_not_found_exception_when_making_move() throws Exception {
        when(kalahaService.makeMove(any(Integer.class), any(Integer.class)))
                .thenThrow(new KalahaGameNotFoundException(ErrorCode.GAME_NOT_FOUND, "Game not found with game id : "));
        this.mockMvc.perform(put(KalahConstants.GAME_ENDPOINT + "/{gameId}/pits/{pitId}", 1, 9)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.comparesEqualTo(101)));
    }

    @Test
    public void should_check_invalid_pit_exception_when_making_move() throws Exception {
        when(kalahaService.makeMove(any(Integer.class), any(Integer.class)))
                .thenThrow(new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Selected pit is empty"));
        this.mockMvc.perform(put(KalahConstants.GAME_ENDPOINT + "/{gameId}/pits/{pitId}", 1, 9)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.comparesEqualTo(102)));
    }

    @Test
    public void should_check_invalid_stone_exception_when_making_move() throws Exception {
        when(kalahaService.createGame(any(Integer.class), any(Integer.class)))
                .thenThrow(new KalahaInvalidStoneCountException(ErrorCode.INVALID_STONES, "invalid stones"));
        this.mockMvc.perform(post(KalahConstants.GAME_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.comparesEqualTo(103)));
    }

    @Test
    public void should_check_game_over_exception_when_making_move() throws Exception {
        when(kalahaService.makeMove(any(Integer.class), any(Integer.class)))
                .thenThrow(new KalahGameOverException(ErrorCode.GAME_OVER, "Game is already over"));
        this.mockMvc.perform(put(KalahConstants.GAME_ENDPOINT + "/{gameId}/pits/{pitId}", 1, 9)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.comparesEqualTo(105)));
    }

    @Test
    public void should_check_any_other_exception_when_making_move() throws Exception {
        when(kalahaService.makeMove(any(Integer.class), any(Integer.class)))
                .thenThrow(new NullPointerException("Game is already over"));
        this.mockMvc.perform(put(KalahConstants.GAME_ENDPOINT + "/{gameId}/pits/{pitId}", 1, 9)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.comparesEqualTo(0)));
    }

    @Test
    public void should_check_any_other_exception_when_creating_game() throws Exception {
        when(kalahaService.createGame(any(Integer.class), any(Integer.class)))
                .thenThrow(new NullPointerException("Game is already over"));
        this.mockMvc.perform(post(KalahConstants.GAME_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.comparesEqualTo(0)));
    }
}
