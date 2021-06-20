package com.games.kalah.repository;

import com.games.kalah.domain.KalahaGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KalahaGameRepository extends JpaRepository<KalahaGame, Long> {
}
