package com.games.kalah.domain;

import com.games.kalah.constants.KalahConstants;
import com.games.kalah.exception.*;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
public class AnotherBoard {/*

    private int pitCount;
    private int stoneCount;
    private int player1Kalaha;
    private int player2FirstPitIndex;
    private int player2Kalaha;
    private Status status;
    private Player winner;
    private Player currentPlayer;

    public AnotherBoard(int pitCount, int stoneCount) throws KalahaException {
        isValidStonesCount(stoneCount);
        isValidPitCount(pitCount);
        this.stoneCount = stoneCount;
        this.pitCount = pitCount;
        this.player1Kalaha = pitCount / 2 - 1;
        this.player2FirstPitIndex = player1Kalaha + 1;
        this.player2Kalaha = pitCount - 1;
    }

    *//**
     * Checks and validates the given pitCount.
     * pitCount minimum value is 6 and it must be a factor of 2
     * @throws KalahaInvalidPitCountException
     * @param pitCount the pitCount
     * @return boolean
     *//*
    private boolean isValidPitCount(int pitCount) throws KalahaInvalidPitCountException {
        if (pitCount < 6 || pitCount % 2 != 0)
            throw new KalahaInvalidPitCountException(ErrorCode.INVALID_PIT_COUNT,
                    "PitCount minimum value is 6 and it must be a factor of 2");
        return true;
    }

    *//**
     * Checks the stones count given is valid. A valid stone count it greater than zero
     * @param stonesCount
     * @return true if stones count is greater than zero
     * @throws KalahaException
     *//*
    public boolean isValidStonesCount(int stonesCount) throws KalahaException {
        if (stonesCount < 1)
            throw new KalahaInvalidStoneCountException(ErrorCode.INVALID_STONES, "Stone must be greater than zero");
        return true;
    }


    *//**
     *
     * @param pitId
     * @param pitStonesCount
     * @throws KalahaException
     *//*
    public void move(int pitId, int pitStonesCount, Player currentPlayer) throws KalahaException {
        int tmpPitStones = pitStonesCount;
        int nextPit = pitId + 1;
        int restrictedPitId = currentPlayer == Player.PLAYER_1 ? this.player2Kalaha : this.player1Kalaha;
        //int playerKalah = currentPlayer == Player.PLAYER_1 ? KalahConstants.PLAYER1_KALAH : KalahConstants.PLAYER2_KALAH;
        //can check if the count of current pit equals distance of pit from its player's kalaha.
        //next player is still current player and give chance to run again

        while (tmpPitStones > 0) {
            if (nextPit > this.player2Kalaha)
                nextPit = 0;

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
            }
            nextPit++;
        }
        //next pit is 7 or 14 i.e last stone was dumped in Kalaha, so next player is current player so no need to change
        if (nextPit - 1 != playerKalah)
            this.game.setCurrentPlayer(getNextPlayer(currentPlayer));
    }

    *//**
     * Checks if the pit id lies between the valid range and is not the kalah.
     * @param pitId
     * @return boolean
     * @throws KalahaException
     *//*
    public boolean isValidPitId(int pitId) throws KalahaException {
        if (isThePitKalaha.test(pitId))
            throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Selected Pit is Kalah");

        if (isPitIdBetweenPlayer1Pits.test(pitId) || isPitIdBetweenPlayer2Pits.test(pitId))
            return true;
        throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Pit Id should be between 1-6 and 8-13");
    }

    *//**
     * Checks if the pits lies in the {@link Player#PLAYER_2} pits range
     * {@link Player#PLAYER_2} pits range is between 7 - 12
     * This is a zero based index.
     * @param pitId
     * @return true if the pit is between the range
     *//*
    Predicate<Integer> isPitIdBetweenPlayer2Pits = (p) ->
            p > KalahConstants.PLAYER2_START_PIT_ID && p < KalahConstants.PLAYER2_END_PIT_ID;

    *//**
     * Checks if the pits lies in the {@link Player#PLAYER_1} pits range
     * {@link Player#PLAYER_1} pits range is between 0 - 5
     * This is a zero based index.
     * @param pitId
     * @return true if the pit is between the range
     *//*
    Predicate<Integer> isPitIdBetweenPlayer1Pits = (p) ->
            p > KalahConstants.PLAYER1_START_PIT_ID && p < KalahConstants.PLAYER1_END_PIT_ID;

    *//**
     * Checks if the pit is either player 1 kalah(index = 6) or player 2 kalah(index = 13).
     * This is zero based index checking.
     * @param pitId
     * @return true if The pit is 6 or 13
     *//*
    Predicate<Integer> isThePitKalaha = (p) ->
            p == KalahConstants.PLAYER1_KALAH || p == KalahConstants.PLAYER2_KALAH;*/

}
