package com.games.kalah;

import com.games.kalah.domain.boards.BoardFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.net.UnknownHostException;


@SpringBootApplication
public class KalahApplication implements Serializable {

    public static void main(String[] args) throws UnknownHostException {

        SpringApplication.run(KalahApplication.class, args);
    }

    @Bean
    public BoardFactory getBoardFactory() {
        return new BoardFactory();
    }

}

