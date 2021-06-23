package com.games.kalah.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class KalahaGameTest {

    @Test
    public void should_create_new_kalah_game() {
        KalahaGame game = new KalahaGame(14, 6);
        assertNotNull(game);
        assertNotNull(game.getPits());
    }

    @Test
    public void should_newly_create_kalah_have_running_status() {
        KalahaGame game = new KalahaGame(14, 6);
        assertEquals(Status.RUNNING, game.getStatus());
    }

    @Test
    public void should_newly_create_kalah_have_starting_as_player_one() {
        KalahaGame game = new KalahaGame(14, 6);
        assertEquals(Player.PLAYER_1, game.getCurrentPlayer());
    }

    @Test
    public void should_newly_create_kalah_have_pits_with_starting_pit_stones() {
        KalahaGame game = new KalahaGame(14, 6);
        assertEquals(6, game.getPit(0).getStones());
    }

    @Test
    public void should_newly_create_kalah_have_kalah_house_at_6_and_13() {
        KalahaGame game = new KalahaGame(14, 6);
        assertTrue(game.getPit(6) instanceof KalahaHouse);
        assertTrue(game.getPit(13) instanceof KalahaHouse);
    }

    @Test
    public void should_create_new_kalah_game_with_10_pits() {
        KalahaGame game = new KalahaGame(10, 4);
        assertNotNull(game);
        assertNotNull(game.getPits());
        assertEquals(10, game.getPits().size());
        assertTrue(game.getPit(4) instanceof KalahaHouse);
        assertTrue(game.getPit(9) instanceof KalahaHouse);
        assertEquals(4, game.getPit(0).getStones());
        assertEquals(0, game.getPit(4).getStones());
    }

    @Test
    public void test_start_and_end_pit_index_of_players_in_10_pit_kalah() {
        KalahaGame game = new KalahaGame(10, 4);
        assertEquals(4, game.getPlayer1KalahaIndex());
        assertEquals(5, game.getPlayer2FirstPitIndex());
        assertEquals(9, game.getPlayer2KalahaIndex());
    }

    @Test
    public void test_start_and_end_pit_index_of_players_in_18_pit_kalah() {
        KalahaGame game = new KalahaGame(18, 4);
        assertEquals(8, game.getPlayer1KalahaIndex());
        assertEquals(9, game.getPlayer2FirstPitIndex());
        assertEquals(17, game.getPlayer2KalahaIndex());
    }

    @Test
    public void all_players_should_have_stones_when_game_starts() {
        KalahaGame game = new KalahaGame(14, 6);
        Optional<Player> optional= game.getPlayerWithNoStones();
        assertEquals(false, optional.isPresent());
    }

    @Test
    public void should_return_player_1_when_no_stones_left_in_pits() {
        KalahaGame game = new KalahaGame(14, 6);;
        IntStream.rangeClosed(0, 5).forEach(p -> game.getPit(p).clear());
        Optional<Player> optional1= game.getPlayerWithNoStones();
        assertEquals(Player.PLAYER_1, optional1.get());
    }

    @Test
    public void should_return_player_2_when_no_stones_left_in_pits() {
        KalahaGame game = new KalahaGame(14, 6);;

        IntStream.rangeClosed(7, 12).forEach(p -> game.getPit(p).clear());
        Optional<Player> optional2= game.getPlayerWithNoStones();
        assertEquals(Player.PLAYER_2, optional2.get());
    }
}
