package com.techeer.port.voilio.domain.board.dto.request;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.Category;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class BoardUpdateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category1;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category2;

    @URL
    @NotBlank
    private String thumbnail_url;

    public Board toEntity(Board board){
        board.setBoard(
                this.title,
                this.content,
                this.category1,
                this.category2,
                this.thumbnail_url
        );
        return board;
    }
}
