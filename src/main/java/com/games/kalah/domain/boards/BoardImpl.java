package com.games.kalah.domain.boards;

import com.games.kalah.domain.ErrorCode;
import com.games.kalah.domain.KalahaGame;
import com.games.kalah.domain.Player;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import lombok.Getter;

@Getter
public class BoardImpl extends AbstractBoard {

    private KalahaGame game;

    public BoardImpl(KalahaGame game) {
        this.game = game;
        this.pitCount = game.getNoOfPits();
        this.player1KalahaIndex = (pitCount / 2) - 1;
        this.player2FirstPitIndex = player1KalahaIndex + 1;
        this.player2KalahaIndex = pitCount - 1;
    }


    @Override
    public void sowStones(int pitId) throws KalahaException {
        int pitStones = this.game.getPit(pitId).getStones();
        Player currentPlayer = this.game.getCurrentPlayer();
        isValidPitIdForMove(pitId, currentPlayer);

        int tmpPitStones = pitStones;
        int nextPit = pitId + 1;
        int restrictedPitId = currentPlayer == Player.PLAYER_1 ? player2KalahaIndex : player1KalahaIndex;
        int playerKalah = currentPlayer == Player.PLAYER_1 ? player1KalahaIndex : player2KalahaIndex;
        //can check if the count of current pit equals distance of pit from its player's kalaha.
        //next player is still current player and give chance to run again

        while (tmpPitStones > 0) {
            if (nextPit > player2KalahaIndex)
                nextPit = 0;

            //check if last stone and current pit is empty, then calculate opposite pit, get its stone ,
            // add opposite pit stones with current pit stones and then add them to kalah
            if (isLastStone(tmpPitStones - 1) && isPitIdBetweenPlayerPits(currentPlayer, nextPit) && this.game.getPit(nextPit).isEmpty()) {
                int oppositePit = calculateOppositePit(nextPit);
                this.game.getPit(playerKalah).addStones(1 + this.game.getPit(oppositePit).getStones());
                this.game.getPit(nextPit).clear();
                this.game.getPit(oppositePit).clear();
                tmpPitStones--;
                continue;
            }
            if (nextPit != restrictedPitId) {
                tmpPitStones--;
                this.game.getPit(nextPit).addStones(1);
            }
            nextPit++;
        }
        this.game.getPit(pitId).clear();
        //next pit is 7 or 14 i.e last stone was dumped in Kalaha, so next player is current player so no need to change
        updatePlayerTurn(nextPit - 1, currentPlayer);

    }

    /**
     * Getter for current player from KalahaGame
     *
     * @return current player running the game
     */
    public Player getCurrentPlayer() {
        return this.game.getCurrentPlayer();
    }

    /**
     * Updates player's turn in {@link KalahaGame#currentPlayer}.
     *
     * @param lastPit
     */
    public void updatePlayerTurn(int lastPit, Player player) {
        if ((player == Player.PLAYER_1 && lastPit != player1KalahaIndex)
                || (player == Player.PLAYER_2 && lastPit != player2KalahaIndex))
            this.game.setCurrentPlayer(getNextPlayer(player));
    }



    /**
     * Checks if the stone is the last one. Helps is determining whether the player keeps the turn or whether the pit
     * where this last stone is being put is an empty pit.
     *
     * @param i
     * @return true if last stone
     */
    private boolean isLastStone(int i) {
        return i == 0;
    }


}
