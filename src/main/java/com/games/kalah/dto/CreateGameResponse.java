package com.games.kalah.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateGameResponse {

    private String id;
    private String uri;

    public CreateGameResponse(long id, String uri) {
        this.id = String.valueOf(id);
        this.uri = uri;
    }
}
