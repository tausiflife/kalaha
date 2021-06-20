package com.games.kalah.service;

import com.games.kalah.domain.*;
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

@Service
public class KalahaService implements KalahaServiceApi {

    @Autowired
    private KalahaGameRepository kalahaGameRepository;

    @Autowired
    private Validator validator;

    @Override
    public long createGame(int pitCount, int stones) throws KalahaException {
        validator.isValidStonesCount(stones);
        KalahaGame game = KalahaFactory.createKalahaGame(pitCount, stones);
        game = kalahaGameRepository.save(game);
        return game.getId();
    }

    @Override
    public List<KalahaPit> makeMove(int gameId, int pitId) throws KalahaException {
        Optional<KalahaGame> optional = kalahaGameRepository.findById(Long.valueOf(gameId));
        if (!optional.isPresent())
            throw new KalahaGameNotFoundException(ErrorCode.GAME_NOT_FOUND, "Game not found with game id : " + gameId);
        KalahaGame game = optional.get();
        if (game.getStatus().equals(Status.OVER))
            throw new KalahGameOverException(ErrorCode.GAME_OVER, "Game is already over");
        int pitStonesCount = game.getPit(pitId).getStones();
        if(pitStonesCount == 0) //Empty pit
            throw new KalahaInvalidPitIdException(ErrorCode.INVALID_PIT, "Selected pit is empty");
        Board board = new Board(game, pitId - 1);
        board.move();
        kalahaGameRepository.save(game);
        return Collections.unmodifiableList(game.getPits());
    }


}
