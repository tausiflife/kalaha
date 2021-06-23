package com.games.kalah;

import com.games.kalah.domain.boards.BoardFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class KalahApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahApplication.class, args);
	}

	@Bean
	public BoardFactory getBoardFactory() {
		return new BoardFactory();
	}

}
