package com.games.kalah.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Entity
@Table(name = "KALAHA")
@Setter
@Getter
@NoArgsConstructor
public class KalahaGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_KALAH_GAME")
    @SequenceGenerator(name = "SEQ_KALAH_GAME", sequenceName = "SEQ_KALAH_GAME", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<KalahaPit> pits;
    ;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CURRENT_PLAYER")
    private Player currentPlayer;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "WINNER")
    private Player winner;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "PIT_COUNT")
    private int noOfPits;

    @Column(name = "STONES_PER_PIT")
    private int startingStonesPerPit;


    /**
     * Construct a Kalaha Game.
     * @param noOfPits
     * @param pitStones
     */
    public KalahaGame(int noOfPits, int pitStones) {
        this.noOfPits = noOfPits;
        this.startingStonesPerPit = pitStones;
        pits = new ArrayList<>();
        int player1KalahaIndex = (noOfPits / 2) - 1;
        int player2KalahaIndex = noOfPits - 1;

        for (int i = 0; i < noOfPits; i++) {
            KalahaPit pit = null;
            if (i == player1KalahaIndex || i == player2KalahaIndex) {
                pit = new KalahaHouse(i, pitStones);
            } else {
                pit = new KalahaPit(i, pitStones);
            }
            pit.setGame(this);
            pits.add(pit);
        }
        this.currentPlayer = Player.PLAYER_1;
        this.status = Status.RUNNING;
    }

    @PrePersist
    protected void prePersist() {
        if (this.createdDate == null)
            createdDate = LocalDateTime.now();

    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }



    /**
     * Zero based index
     *
     * @param pitIndex
     * @return
     */
    public KalahaPit getPit(int pitIndex) {
        return this.pits.get(pitIndex);
    }

    /**
     * This method check from the {@link KalahaGame} status of the game.
     * @return boolean if status is equal to {@link Status#GAME_OVER}
     */
    public Boolean isGameOver() {
        return this.status.equals(Status.GAME_OVER);
    }

    /**
     * Getter for player 1 Kalah index
     * @return
     */
    public int getPlayer1KalahaIndex() {
        return (noOfPits / 2) - 1;
    }

    /**
     * Getter for player 2 starting pit index
     * @return
     */
    public int getPlayer2FirstPitIndex() {
        return getPlayer1KalahaIndex() + 1;
    }

    /**
     * Getter for player 2 Kalah index
     * @return
     */
    public int getPlayer2KalahaIndex() {
        return noOfPits - 1;
    }


    /**
     * Checks of the {@link Player} has any stone left in any of the pits.
     * @param
     * @return
     */
    public Optional<Player> getPlayerWithNoStones() {
        boolean player1HasAnyStoneLeft = IntStream.rangeClosed(0, getPlayer1KalahaIndex() - 1).anyMatch(p -> this.getPit(p).getStones() > 0);
        boolean player2HasAnyStoneLeft = IntStream.rangeClosed(getPlayer2FirstPitIndex(), getPlayer2KalahaIndex() - 1).anyMatch(p -> this.getPit(p).getStones() > 0);
        if (!player1HasAnyStoneLeft)
            return Optional.of(Player.PLAYER_1);
        else if (!player2HasAnyStoneLeft)
            return Optional.of(Player.PLAYER_2);
        return Optional.ofNullable(null);
    }

}
