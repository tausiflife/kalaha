package com.games.kalah.repository;

import com.games.kalah.domain.KalahaFactory;
import com.games.kalah.domain.KalahaGame;
import com.games.kalah.domain.Player;
import com.games.kalah.domain.Status;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class KalahaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private KalahaGameRepository kalahaGameRepository;


    @Test
    public void should_store_a_kalaha_game() {
        KalahaGame game = KalahaFactory.createKalahaGame(14, 6);
        game = kalahaGameRepository.save(game);

        //then
        assertNotNull(game);
        assertNotNull(game.getPits());
    }

    @Test
    public void should_find_kalaha_game_by_id() {
        //Given
        KalahaGame game = KalahaFactory.createKalahaGame(14, 6);
        entityManager.persist(game);

        //when
        game = kalahaGameRepository.findById(game.getId()).get();
        //game.getPit(0).addStones(1);
        //game = kalahaGameRepository.save(game);

        //then
        assertNotNull(game);
        assertEquals(game.getPit(0).getStones() , 6);
    }

    @Test
    public void should_have_starting_player_1() {
        //Given
        KalahaGame game = KalahaFactory.createKalahaGame(14, 6);
        entityManager.persist(game);

        //when
        game = kalahaGameRepository.findById(game.getId()).get();
        //then
        assertNotNull(game);
        assertEquals(game.getCurrentPlayer() , Player.PLAYER_1);
    }

    @Test
    public void should_have_starting_game_status_as_running() {
        //Given
        KalahaGame game = KalahaFactory.createKalahaGame(14, 6);
        entityManager.persist(game);

        //when
        game = kalahaGameRepository.findById(game.getId()).get();
        //then
        assertNotNull(game);
        assertEquals(game.getStatus() , Status.RUNNING);
    }


    @Test
    public void should_update_kalaha_game_pit_stones() {
        //Given
        KalahaGame game = KalahaFactory.createKalahaGame(14, 6);
        entityManager.persist(game);

        //when
        game = kalahaGameRepository.findById(game.getId()).get();
        game.getPit(0).addStones(1);
        entityManager.persist(game);

        //then
        assertNotNull(game);
        assertEquals(game.getPit(0).getStones() , 7);
    }

    @After
    public void teardown() {
    }
}
