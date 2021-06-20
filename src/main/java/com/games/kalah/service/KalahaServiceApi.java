package com.games.kalah.service;

import com.games.kalah.domain.KalahaGame;
import com.games.kalah.domain.KalahaPit;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaGameNotFoundException;

import java.util.List;

public interface KalahaServiceApi {

    /**
     * Creates a new {@link com.games.kalah.domain.KalahaGame } game calling the factory class of
     * { @link com.games.kalah.domain.KalahaFactory }
     * @param pitCount
     * @param stones
     * @return gameId created in the database.
     * @throws KalahaException
     */
    long createGame(int pitCount, int stones) throws KalahaException;

    /**
     *
     * @param gameId
     * @param pitId
     * @return unmodifiable List of {@link com.games.kalah.domain.KalahaPit } each containing its index and
     * number of stones.
     * @throws KalahaException
     */
    List<KalahaPit> makeMove(int gameId, int pitId) throws KalahaException;
}
