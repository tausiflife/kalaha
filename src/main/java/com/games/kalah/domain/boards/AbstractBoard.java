package com.games.kalah.domain.boards;

import com.games.kalah.domain.ErrorCode;
import com.games.kalah.domain.Player;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import com.games.kalah.exception.KalahaInvalidPlayerException;

public abstract class AbstractBoard implements Board {
    protected int pitCount;
    protected int player1KalahaIndex;
    protected int player2FirstPitIndex;
    protected int player2KalahaIndex;

    /**
     * Getter for next player after the current turn is over.
     *
     * @return {@link Player} as next player
     */
    public Player getNextPlayer(Player player) {
        return player == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
    }

    /**
     * Checks if the current pit is between current players pit.
     *
     * @param currentPlayer
     * @param pitId
     * @return boolean
     */
    public boolean isPitIdBetweenPlayerPits(Player currentPlayer, int pitId) {
        return currentPlayer == Player.PLAYER_1 ? isPitIdBetweenPlayer1Pits(pitId) : isPitIdBetweenPlayer2Pits(pitId);
    }

    /**
     * Getter for opposite pitId to the current pit.
     *
     * @return pitId of opposite pit
     */
    public int calculateOppositePit(int pitId) {
        return this.pitCount - pitId - 2;
    }


    /**
     * Checks if the pit id lies between the valid range and is not the kalah.
     *
     * @param pitId
     * @return boolean
     * @throws KalahaException
     */
    public boolean isValidPitIdForMove(int pitId, Player player) throws KalahaException {
        if (isThePitKalah(pitId))
            throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Selected Pit is Kalah");

        if (pitId < 0 || pitId > player2KalahaIndex)
            throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Pit Id should be between range");

        if ((player == Player.PLAYER_1 && isPitIdBetweenPlayer1Pits(pitId))
                || (player == Player.PLAYER_2 && isPitIdBetweenPlayer2Pits(pitId)))
            return true;
        throw new KalahaInvalidPlayerException(ErrorCode.INVALID_PLAYER, "Wrong player");
    }

    /**
     * Checks if the pit is either player 1 kalah(index = 6) or player 2 kalah(index = 13).
     * This is zero based index checking.
     *
     * @param pitId
     * @return true if The pit is 6 or 13
     */
    public boolean isThePitKalah(int pitId) {
        return pitId == player1KalahaIndex || pitId == player2KalahaIndex;
    }

    /**
     * Checks if the pit is in player 2 pit range i.e 7-12, both inclusive.
     * This is zero based index checking.
     *
     * @param pitId
     * @return boolean
     */
    public boolean isPitIdBetweenPlayer2Pits(int pitId) {
        return pitId >= player2FirstPitIndex && pitId <= player2KalahaIndex;
    }

    /**
     * Checks if the pit is in player 1 pit range i.e 0-5, both inclusive.
     * This is zero based index checking.
     *
     * @param pitId
     * @return boolean
     */
    public boolean isPitIdBetweenPlayer1Pits(int pitId) {
        return pitId >= 0 && pitId <= player1KalahaIndex - 1;
    }
}
