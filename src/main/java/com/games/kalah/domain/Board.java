package com.games.kalah.domain;

import com.games.kalah.constants.KalahConstants;
import com.games.kalah.exception.KalahGameOverException;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidPitIdException;

import java.util.stream.IntStream;

public class Board {

    private final int pitId;
    private KalahaGame game;

    public Board(KalahaGame game, int pitId) {
        this.game = game;
        this.pitId = pitId;
    }


    public void move() throws KalahaException {
        isValidPitIdForMove(pitId);
        if(isGameOver())
            throw new KalahGameOverException(ErrorCode.GAME_OVER, "Game is already over");
        int pitStonesCount = getPitStonesCount();
        if(pitStonesCount == 0) //Empty pit
            throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Selected pit is empty");
        int tmpPitStones = pitStonesCount;
        int nextPit = this.pitId + 1;
        Player currentPlayer = getCurrentPlayer();
        int restrictedPitId = currentPlayer == Player.PLAYER_1 ? KalahConstants.PLAYER2_KALAH : KalahConstants.PLAYER1_KALAH;
        int playerKalah = currentPlayer == Player.PLAYER_1 ? KalahConstants.PLAYER1_KALAH : KalahConstants.PLAYER2_KALAH;
        //can check if the count of current pit equals distance of pit from its player's kalaha.
        //next player is still current player and give chance to run again

        while (tmpPitStones > 0) {
            if (nextPit > KalahConstants.PLAYER2_KALAH)
                nextPit = KalahConstants.PLAYER1_START_PIT_ID;

            //check if last stone and current pit is empty, then calculate opposite pit, get its stone ,
            // add opposite pit stones with current pit stones and then add them to kalah
            if(isLastStone(tmpPitStones - 1) && isPitIdBetweenPlayerPits(currentPlayer, pitId) && this.game.getPit(nextPit).isEmpty()) {
                int oppositePit = calculateOppositePit();
                this.game.getPit(playerKalah).addStones( 1 + this.game.getPit(oppositePit).getStones());
                this.game.getPit(nextPit).clear();
                this.game.getPit(oppositePit).clear();
                tmpPitStones--;
            }
            if (nextPit != restrictedPitId) {
                tmpPitStones--;
                this.game.getPit(nextPit).addStones(1);
            }
            nextPit++;
        }
        this.game.getPit(pitId).clear();
        //next pit is 7 or 14 i.e last stone was dumped in Kalaha, so next player is current player so no need to change
        updatePlayerTurn(nextPit - 1);
        updateGameStatus();
    }

    private void updateGameStatus() {
        if (!hasAnyStone(Player.PLAYER_1)) {
            flushToKalah(Player.PLAYER_2);
            gameOver();
        } else if (!hasAnyStone(Player.PLAYER_2)) {
            flushToKalah(Player.PLAYER_1);
            gameOver();
        }

        if (this.game.getStatus() == Status.OVER) {
            int player1Score = this.game.getPit(KalahConstants.PLAYER1_KALAH).getStones();
            int player2Score = this.game.getPit(KalahConstants.PLAYER2_KALAH).getStones();
            if (player1Score > player2Score) {
                this.game.setWinner(Player.PLAYER_1);
            } else if (player1Score < player2Score) {
                this.game.setWinner(Player.PLAYER_2);
            }
        }
    }

    private void gameOver() {
        this.game.setStatus(Status.OVER);
    }

    private void flushToKalah(Player player) {
        int start = player == Player.PLAYER_1 ? KalahConstants.PLAYER1_START_PIT_ID : KalahConstants.PLAYER2_START_PIT_ID;
        int end = player == Player.PLAYER_1 ? KalahConstants.PLAYER1_END_PIT_ID : KalahConstants.PLAYER2_END_PIT_ID;
        IntStream.rangeClosed(start, end).forEach(p -> {
            this.game.getPit(end + 1).addStones(this.game.getPit(p).getStones());
            this.game.getPit(p).clear();
        });
    }

    private boolean hasAnyStone(Player player) {
        int start = player == Player.PLAYER_1 ? KalahConstants.PLAYER1_START_PIT_ID : KalahConstants.PLAYER2_START_PIT_ID;
        int end = player == Player.PLAYER_1 ? KalahConstants.PLAYER1_END_PIT_ID : KalahConstants.PLAYER2_END_PIT_ID;
        return IntStream.rangeClosed(start, end).anyMatch(p -> this.game.getPit(p).getStones() > 0);
    }

    private void updatePlayerTurn(int lastPit) {
       if ((getCurrentPlayer() == Player.PLAYER_1 && lastPit != KalahConstants.PLAYER1_KALAH)
               || (getCurrentPlayer() == Player.PLAYER_2 && lastPit != KalahConstants.PLAYER2_KALAH))
           this.game.setCurrentPlayer(getNextPlayer());
    }

    private Player getNextPlayer() {
        return getCurrentPlayer() == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
    }

    private boolean isPitIdBetweenPlayerPits(Player currentPlayer, int pitId) {
        return currentPlayer == Player.PLAYER_1 ? isPitIdBetweenPlayer1Pits(pitId) : isPitIdBetweenPlayer2Pits(pitId);
    }

    private int calculateOppositePit() {
        return KalahConstants.DEFAULT_PIT_COUNTS - this.pitId - 2;
    }

    private boolean isLastStone(int i) {
        return i == 0;
    }

    private Player getCurrentPlayer() {
        return this.game.getCurrentPlayer();
    }

    private int getPitStonesCount() {
        return this.game.getPit(pitId).getStones();
    }

    /**
     * This method check from the {@link KalahaGame} status of the game.
     * @return boolean if status is equal to {@link Status#OVER}
     */
    private Boolean isGameOver() {
        return this.game.getStatus().equals(Status.OVER);
    }

    /**
     * Checks if the pit id lies between the valid range and is not the kalah.
     * @param pitId
     * @return boolean
     * @throws KalahaException
     */
    public boolean isValidPitIdForMove(int pitId) throws KalahaException {
        if (isThePitKalah(pitId))
            throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Selected Pit is Kalah");

        if (isPitIdBetweenPlayer1Pits(pitId) || isPitIdBetweenPlayer2Pits(pitId))
            return true;
        throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Pit Id should be between 1-6 and 8-13");
    }

    /**
     * Checks if the pit is either player 1 kalah(index = 6) or player 2 kalah(index = 13).
     * This is zero based index checking.
     * @param pitId
     * @return true if The pit is 6 or 13
     */
    public boolean isThePitKalah(int pitId) {
        return pitId == KalahConstants.PLAYER1_KALAH || pitId == KalahConstants.PLAYER2_KALAH;
    }

    public boolean isPitIdBetweenPlayer2Pits(int pitId) {
        return pitId >= KalahConstants.PLAYER2_START_PIT_ID && pitId <= KalahConstants.PLAYER2_END_PIT_ID;
    }

    public boolean isPitIdBetweenPlayer1Pits(int pitId) {
        return pitId >= KalahConstants.PLAYER1_START_PIT_ID && pitId <= KalahConstants.PLAYER1_END_PIT_ID;
    }
}
