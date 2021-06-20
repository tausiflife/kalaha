package com.games.kalah.domain;

import com.games.kalah.constants.KalahConstants;

public class KalahaFactory {

    /**
     * Creates Kalaha game {@link KalahaGame} with default (6) specified stones per pit
     * @return KalahaGame
     */
    public static KalahaGame createKalahaGameWithDefaults() {
        return new KalahaGame(KalahConstants.DEFAULT_PIT_COUNTS, KalahConstants.DEFAULT_PIT_STONES);
    }

    /**
     * Create Kalaha game {@link KalahaGame} with user specified stones per pit.
     * @return KalahaGame
     */
    public static KalahaGame createKalahaGame(int pitCount, int stones) {
        return new KalahaGame(pitCount, stones);
    }
}
