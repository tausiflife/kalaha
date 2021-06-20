package com.games.kalah.controller;

import com.games.kalah.constants.KalahConstants;
import com.games.kalah.domain.KalahaGame;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.service.KalahaService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
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
}
