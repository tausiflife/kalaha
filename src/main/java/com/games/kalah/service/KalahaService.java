package com.games.kalah.service;

import com.games.kalah.domain.*;
import com.games.kalah.domain.boards.Board;
import com.games.kalah.domain.boards.BoardImpl;
import com.games.kalah.domain.boards.BoardFactory;
import com.games.kalah.exception.KalahGameOverException;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.exception.KalahaGameNotFoundException;
import com.games.kalah.exception.KalahaInvalidPitIdException;
import com.games.kalah.repository.KalahaGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class KalahaService implements KalahaServiceApi {

    @Autowired
    private BoardFactory boardFactory;

    @Autowired
    private KalahaGameRepository kalahaGameRepository;

    @Autowired
    private Validator validator;

    @Override
    public long createGame(int pitCount, int stones) throws KalahaException {
        validator.isValidStonesCount(stones);
        KalahaGame game = new KalahaGame(pitCount, stones);
        game = kalahaGameRepository.save(game);
        return game.getId();
    }

    @Override
    public List<KalahaPit> makeMove(int gameId, int pitId) throws KalahaException {
        Optional<KalahaGame> optional = kalahaGameRepository.findById(Long.valueOf(gameId));
        if (!optional.isPresent())
            throw new KalahaGameNotFoundException(ErrorCode.GAME_NOT_FOUND, "Game not found with game id : " + gameId);
        KalahaGame game = optional.get();
        if (game.isGameOver())
            throw new KalahGameOverException(ErrorCode.GAME_OVER, "Game is already over");
        int pitStonesCount = game.getPit(pitId - 1).getStones();
        if (pitStonesCount == 0) //Empty pit
            throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Selected pit is empty");
        Board board = boardFactory.createBoard(game);
        board.sowStones(pitId - 1);
        Optional<Player> playerWithNoStones = game.getPlayerWithNoStones();
        if (playerWithNoStones.isPresent()) {
            moveEveryThingToKalaha(playerWithNoStones.get(), game);
            updateGameStatusToOver(game);
            updateWinner(game);
        }
        kalahaGameRepository.save(game);
        return Collections.unmodifiableList(game.getPits());
    }


    /**
     * Updates the game with the winner. Winner is calculated based on the highest number of stones in the Kalaha pit.
     * @param game
     */
    private void updateWinner(KalahaGame game) {
        int player1Score = game.getPit(game.getPlayer1KalahaIndex()).getStones();
        int player2Score = game.getPit(game.getPlayer2KalahaIndex()).getStones();
        game.setWinner(player1Score > player2Score ? Player.PLAYER_1 : Player.PLAYER_2);
    }

    /**
     * Setter for changing the game status to over in {@link KalahaGame#status}
     */
    private void updateGameStatusToOver(KalahaGame game) {
        game.setStatus(Status.GAME_OVER);
    }

    /**
     * Move all stones from all the pits of a player to {@link KalahaHouse}
     *
     * @param player
     */
    private void moveEveryThingToKalaha(Player player, KalahaGame game) {
        int start = player == Player.PLAYER_1 ? game.getPlayer2FirstPitIndex() : 0;
        int end = player == Player.PLAYER_1 ? game.getPlayer2KalahaIndex() : game.getPlayer1KalahaIndex();
        IntStream.range(start, end).forEach(p -> {
            game.getPit(end).addStones(game.getPit(p).getStones());
            game.getPit(p).clear();
        });
    }


}
