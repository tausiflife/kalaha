package com.games.kalah.controller;

import com.games.kalah.constants.KalahConstants;
import com.games.kalah.domain.KalahaGame;
import com.games.kalah.domain.KalahaPit;
import com.games.kalah.dto.CreateGameResponse;
import com.games.kalah.dto.MoveGameResponse;
import com.games.kalah.exception.KalahaException;
import com.games.kalah.service.KalahaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

@RestController
public class KalahaController {

    private static final String URI_TEMPLATE = "%s://%s:%d%s/%d";

    @Autowired
    private KalahaService gameService;

    @Value("${kalaha.pit.stones:6}")
    private int pitStones;

    @Value("${kalaha.pit.count:14}")
    private int pitCount;

    @PostMapping(value = KalahConstants.GAME_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody(required = false) String body,
                                                         HttpServletRequest request) throws KalahaException {
        long gameId = gameService.createGame(pitCount, pitStones);
        CreateGameResponse response = new CreateGameResponse(gameId, String.format(URI_TEMPLATE,
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                request.getContextPath() + request.getServletPath(),
                gameId));
        return new ResponseEntity<CreateGameResponse>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = KalahConstants.GAME_ENDPOINT + "/{gameId}/pits/{pitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MoveGameResponse> makeMove(@RequestBody(required = false) String body,
                                           @PathVariable(name = "gameId") int gameId,
                                           @PathVariable(name = "pitId") int pitId, HttpServletRequest request) throws KalahaException{
        List<KalahaPit> pits = gameService.makeMove(gameId, pitId);
        MoveGameResponse response = new MoveGameResponse(gameId, String.format(URI_TEMPLATE,
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                request.getContextPath() + request.getServletPath(),
                gameId), pits);
        return new ResponseEntity<MoveGameResponse>(response, HttpStatus.OK);
    }
}
