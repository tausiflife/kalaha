package com.games.kalah.dto;

import com.games.kalah.domain.KalahaPit;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
public class MoveGameResponse {
    private String id;
    private String url;
    private Map<String, String> status;

    public MoveGameResponse(int id, String url, List<KalahaPit> pits) {
        this.id = String.valueOf(id);
        this.url = url;
        this.status = pits.stream().collect(Collectors.toMap(p -> String.valueOf(p.getPitId()), q -> String.valueOf(q.getStones())));
    }
}
