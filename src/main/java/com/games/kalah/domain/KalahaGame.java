package com.games.kalah.domain;

import com.games.kalah.constants.KalahConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private int pitCount;

    @Column(name = "PLAYER1_LAST_PIT_INDEX")
    private int player1LastPitIndex;

    @Column(name = "PLAYER2_FIRST_PIT_INDEX")
    private int player2FirstPitIndex;

    @Column(name = "PLAYER2_LAST_PIT_INDEX")
    private int player2LastPitIndex;

    public KalahaGame(int pitCount, int pitStones) {
        this.pitCount = pitCount;
        pits = new ArrayList<>();
        this.player1LastPitIndex = (pitCount / 2) - 1;
        this.player2FirstPitIndex = player1LastPitIndex + 1;
        this.player2LastPitIndex = pitCount - 1;

        for (int i = 0; i < pitCount; i++) {
            KalahaPit pit = null;
            if (i == player1LastPitIndex || i == player2LastPitIndex) {
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

}
