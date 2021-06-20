package com.games.kalah.service;

import com.games.kalah.domain.KalahaFactory;
import com.games.kalah.domain.KalahaGame;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.repository.KalahaGameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

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
}
