package com.games.kalah.domain.boards;

import com.games.kalah.domain.KalahaGame;

public class BoardFactory {

    /**
     * Create a board containing the game.
     * @param game
     * @return
     */
    public Board createBoard(KalahaGame game) {
        return new BoardImpl(game);
    }
}
