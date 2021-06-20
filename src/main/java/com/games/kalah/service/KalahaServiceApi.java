package com.games.kalah.service;

import com.games.kalah.domain.KalahaGame;
import com.games.kalah.domain.KalahaPit;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaGameNotFoundException;

import java.util.List;

public interface KalahaServiceApi {

    /**
     * Creates a new game calling the factory class of {@link com.games.kalah.domain.KalahaFactory }
     * @param stones
     * @return gameId
     * @throws KalahaException
     */
    long createGame(int pitCount, int stones) throws KalahaException;

    List<KalahaPit> makeMove(int gameId, int pitId) throws KalahaException;
}
