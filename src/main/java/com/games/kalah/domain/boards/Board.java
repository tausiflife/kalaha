package com.games.kalah.domain.boards;

import com.games.kalah.exception.KalahaException;

public interface Board {

    void sowStones(int pitId) throws KalahaException;
}
